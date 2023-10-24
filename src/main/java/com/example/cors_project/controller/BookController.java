package com.example.cors_project.controller;


import com.example.cors_project.entity.Book;
import com.example.cors_project.entity.User;
import com.example.cors_project.repository.BookRepository;
import com.example.cors_project.service.BookService;
import com.example.cors_project.service.BookServiceImpl;
import com.example.cors_project.service.UserActionService;
import com.example.cors_project.service.UserService;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Slf4j
@Controller
public class BookController {

    @Autowired
    private BookService bookService;
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserActionService userActionService;

    @GetMapping("/list")
    public ModelAndView getAllBooks() {
        log.info("/list -> connection");
        ModelAndView mav = new ModelAndView("list-books");
        mav.addObject("books", bookService.getBooks());
        return mav;
    }

    @GetMapping("/addBookForm")
    public ModelAndView addBookForm() {
        ModelAndView mav = new ModelAndView("add-book-form");
        Book book = new Book();
        mav.addObject("book", book);
        return mav;
    }

    @PostMapping("/saveBook")
    public String saveBook(@ModelAttribute Book book,
                           @AuthenticationPrincipal User user) {
        bookService.addBook(book);
        userActionService.addAction(book, user);
        return "redirect:/list";
    }

    @GetMapping("/showUpdateForm")
    public ModelAndView showUpdateForm(@RequestParam Long bookId,
                                       @AuthenticationPrincipal User user   ) {
        ModelAndView mav = new ModelAndView("add-book-form");
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        Book book = new Book();
        if (optionalBook.isPresent()) {
            book = optionalBook.get();
        }

        mav.addObject("book", book);
        Book savedBook = bookRepository.save(book);
        userActionService.editAction(savedBook, user);
        return mav;
    }

    @PostMapping("/deleteBook")
    public String deleteBook(@RequestParam Long bookId,
                             @AuthenticationPrincipal User user,
                             @ModelAttribute Book book) {
        Book bookToDelete = bookRepository.findById(bookId).orElse(null);
        userActionService.deleteAction(bookToDelete, user);
        bookRepository.deleteById(bookId);
        return "redirect:/list";
    }
}