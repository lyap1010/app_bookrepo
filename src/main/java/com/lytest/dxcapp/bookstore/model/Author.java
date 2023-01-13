package com.lytest.dxcapp.bookstore.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;

@Entity
public class Author {

    @JoinColumn(name="book_isbn", nullable=false)
    private String bookIsbn;

    @Id
    @GeneratedValue
    private int id;
    private String name;
    private Date birthday;

    public String getBookIsbn() {
        return bookIsbn;
    }

    public void setBookIsbn(String bookIsbn) {
        this.bookIsbn = bookIsbn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return id == author.id && bookIsbn.equals(author.bookIsbn) && name.equals(author.name) && birthday.equals(author.birthday);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookIsbn, id, name, birthday);
    }
}
