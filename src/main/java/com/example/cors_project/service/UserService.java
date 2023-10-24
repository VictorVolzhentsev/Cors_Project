package com.example.cors_project.service;

import com.example.cors_project.dto.UserDto;
import com.example.cors_project.entity.User;
import org.springframework.security.access.annotation.Secured;

import java.util.List;

public interface UserService {

    void saveUser(UserDto userDto);

    User findUserByEmail(String email);

    @Secured("ROLE_ADMIN")
    List<UserDto> findAllUsers();

    User getCurrentUser();

}