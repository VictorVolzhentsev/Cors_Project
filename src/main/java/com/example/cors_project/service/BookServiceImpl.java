package com.example.cors_project.service;

import com.example.cors_project.entity.Book;
import com.example.cors_project.entity.User;
import com.example.cors_project.repository.BookRepository;
import com.example.cors_project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService{

    private final UserService userService;

    private final BookRepository bookRepository;


    @Override
    public List<Book> getBooks() {

        return bookRepository.findBooksByUser(userService.getCurrentUser());
    }

    @Override
    public void addBook(Book book) {
        book.setUser(userService.getCurrentUser());
        bookRepository.save(book);
    }

}