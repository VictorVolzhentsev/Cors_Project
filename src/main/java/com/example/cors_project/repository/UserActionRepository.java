package com.example.cors_project.repository;

import com.example.cors_project.entity.UserAction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserActionRepository extends JpaRepository<UserAction, Long> {
}