package com.lytest.dxcapp.bookstore.controller;

import com.lytest.dxcapp.bookstore.model.Book;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
class BookModelAssembler implements RepresentationModelAssembler<Book, EntityModel<Book>> {

    @Override
    public EntityModel<Book> toModel(Book book) {
        return EntityModel.of(book, //
                linkTo(methodOn(BookController.class).one(book.getIsbn())).withSelfRel(),
                linkTo(methodOn(BookController.class).all()).withRel("employees"));
    }
}