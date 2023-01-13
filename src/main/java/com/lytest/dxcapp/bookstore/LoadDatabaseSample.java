package com.lytest.dxcapp.bookstore;

import com.lytest.dxcapp.bookstore.data.BookRepository;
import com.lytest.dxcapp.bookstore.model.Author;
import com.lytest.dxcapp.bookstore.model.Book;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Date;

@Configuration
public class LoadDatabaseSample {

    @Bean
    CommandLineRunner initDatabase(BookRepository bookRepository) {
        return args -> {
            Book book1 = new Book();
            book1.setIsbn("ISBN-BOOK1");
            book1.setGenre("Fiction");
            book1.setPrice(20);
            book1.setYear(2020);
            book1.setTitle("Year Book - 2020");
            Author b1a1 = new Author();
            b1a1.setBookIsbn(book1.getIsbn());
            b1a1.setName("Author ABC");
            b1a1.setBirthday(new Date());
            Author b1a2 = new Author();
            b1a2.setBookIsbn(book1.getIsbn());
            b1a2.setName("Author DEF");
            b1a2.setBirthday(new Date());
            book1.setAuthors(Arrays.asList(b1a1, b1a2));
            bookRepository.save(book1);

            Book book2 = new Book();
            book2.setIsbn("ISBN-BOOK2");
            book2.setGenre("Non-Fiction");
            book2.setPrice(25);
            book2.setYear(2019);
            book2.setTitle("NonFictionBook");

            Author b2a1 = new Author();
            b2a1.setBookIsbn(book2.getIsbn());
            b2a1.setName("Author PQRS");
            b2a1.setBirthday(new Date());

            Author b2a2 = new Author();
            b2a2.setBookIsbn(book2.getIsbn());
            b2a2.setName("Author XYZ");
            b2a2.setBirthday(new Date());

            book2.setAuthors(Arrays.asList(b2a1, b2a2));
            bookRepository.save(book2);
        };
    }
}
