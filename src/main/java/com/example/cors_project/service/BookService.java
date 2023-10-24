package com.example.cors_project.service;

import com.example.cors_project.entity.Book;
import org.springframework.security.access.annotation.Secured;

import java.util.List;


public interface BookService {

    @Secured("ROLE_USER")
    List<Book> getBooks();

    @Secured("ROLE_USER")
    void addBook(Book book);

}