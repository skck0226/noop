package com.ajou.noop.controller;

import com.ajou.noop.domain.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/")
    public List<User> allUser(){

        return
    }
}
