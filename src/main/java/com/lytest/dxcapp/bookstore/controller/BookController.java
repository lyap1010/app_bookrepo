package com.lytest.dxcapp.bookstore.controller;

import com.lytest.dxcapp.bookstore.data.AuthorRepository;
import com.lytest.dxcapp.bookstore.data.BookRepository;
import com.lytest.dxcapp.bookstore.exceptions.BookNotFoundException;
import com.lytest.dxcapp.bookstore.model.Book;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class BookController {

    private BookRepository bookRepository;
    private AuthorRepository authorRepository;
    private final BookModelAssembler assembler;

    BookController(BookRepository bookRepository, AuthorRepository authorRepository, BookModelAssembler assembler) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.assembler = assembler;
        this.authorRepository = authorRepository;
    }

    @PostMapping("/api/book")
    ResponseEntity<?> newBook(@RequestBody Book book) {
        EntityModel<Book> entityModel = assembler.toModel(bookRepository.save(book));
        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @PutMapping("/api/book/{isbn}")
    ResponseEntity<?> updateBook(@RequestBody Book updatedBook, @PathVariable String isbn) {
        Book result = bookRepository.findById(isbn)
                .map(book -> {
                    book.setTitle(updatedBook.getTitle());
                    book.setYear(updatedBook.getYear());
                    book.setPrice(updatedBook.getPrice());
                    book.setGenre(updatedBook.getGenre());
                    book.getAuthors().clear();
                    book.getAuthors().addAll(updatedBook.getAuthors());
                    return bookRepository.save(book);
                })
                .orElseGet(() -> {
                    updatedBook.setIsbn(isbn);
                    return bookRepository.save(updatedBook);
                });

        EntityModel<Book> entityModel = assembler.toModel(result);
        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @GetMapping("/api/search")
    CollectionModel<EntityModel<Book>> search(@RequestParam(required = false) String title, @RequestParam(required = false) String author) {
        if (title == null && author == null) {
            return all();
        }

        Set<Book> resultBooks = new HashSet<>();

        if (title != null) {
            resultBooks.addAll(bookRepository.findByTitle(title));
        }

        if (author != null) {
            resultBooks.addAll(authorRepository.findBooksByName(author));
        }

        List<EntityModel<Book>> booksEntityList = resultBooks.stream().map(book ->
                EntityModel.of(book,
                        linkTo(methodOn(BookController.class).one(book.getIsbn())).withSelfRel(),
                        linkTo(methodOn(BookController.class).all()).withRel("books"))
        ).collect(Collectors.toList());

        return CollectionModel.of(booksEntityList, linkTo(methodOn(BookController.class).all()).withSelfRel());
    }

    @DeleteMapping("/api/book/{isbn}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ResponseEntity<?> deleteBook(@PathVariable String isbn) {
        try {
            bookRepository.deleteById(isbn);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/book/{isbn}")
    public EntityModel<Book> one(@PathVariable String isbn) {
        Book book = bookRepository.findById(isbn) //
                .orElseThrow(() -> new BookNotFoundException(isbn));
        return assembler.toModel(book);
    }

    ;

    @GetMapping("/api/books")
    CollectionModel<EntityModel<Book>> all() {
        List<EntityModel<Book>> books = bookRepository.findAll().stream().map(book ->
                EntityModel.of(book,
                        linkTo(methodOn(BookController.class).one(book.getIsbn())).withSelfRel(),
                        linkTo(methodOn(BookController.class).all()).withRel("books"))
        ).collect(Collectors.toList());

        return CollectionModel.of(books, linkTo(methodOn(BookController.class).all()).withSelfRel());
    }
}
