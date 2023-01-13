package com.lytest.dxcapp.bookstore.data;

import com.lytest.dxcapp.bookstore.model.Author;
import com.lytest.dxcapp.bookstore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Integer> {

    @Query("SELECT b FROM Book b JOIN b.authors a WHERE a.name = :name")
    List<Book> findBooksByName(String name);

}
