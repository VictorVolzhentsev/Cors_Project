package com.example.cors_project.controller;


import com.example.cors_project.dto.UserDto;
import com.example.cors_project.entity.Role;
import com.example.cors_project.entity.User;
import com.example.cors_project.service.UserService;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Controller
public class SecurityController {

    private UserService userService;

    public SecurityController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/index")
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    @GetMapping("/redirect")
    public String redirect() {
        List<Role> userRoles = userService.getCurrentUser().getRoles();
        for (Role role : userRoles) {
            if (role.getName().equals("ROLE_ADMIN")) {
                return "redirect:/users";
            }
            if (role.getName().equals("ROLE_USER")) {
                return "redirect:/list";
            }
        }
        throw new RuntimeException("Роль не найдена");
    }

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
                               BindingResult result,
                               Model model) {
        User existingUser = userService.findUserByEmail(userDto.getEmail());

        if (existingUser != null && existingUser.getEmail() != null & !existingUser.getEmail().isEmpty()) {
            result.rejectValue("email", null,
                    "На этот email уже зарегистрирована учетная запись.");
        }

        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            return "/register";
        }
        userService.saveUser(userDto);
        return "redirect:/register?success";
    }

    @GetMapping("/users")
    public String users(Model model) {
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }
}