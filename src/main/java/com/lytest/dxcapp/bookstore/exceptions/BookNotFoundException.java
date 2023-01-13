package com.lytest.dxcapp.bookstore.exceptions;

public class BookNotFoundException extends RuntimeException{

    public BookNotFoundException(String isbn) {
        super("Book with isbn ["+isbn+"] not found");
    }

}
