package com.example.cors_project.repository;


import com.example.cors_project.entity.Book;
import com.example.cors_project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findBooksByUser(User user);
}