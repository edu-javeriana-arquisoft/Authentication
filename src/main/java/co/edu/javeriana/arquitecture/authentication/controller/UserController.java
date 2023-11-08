package co.edu.javeriana.arquitecture.authentication.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import co.edu.javeriana.arquitecture.authentication.entity.Status;
import co.edu.javeriana.arquitecture.authentication.entity.User;
import co.edu.javeriana.arquitecture.authentication.service.UserService;

@RestController
@RequestMapping("/")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public Status addUser(@Valid @RequestBody User user){
        userService.addUser(user);
        return new Status("User created");
    }

}