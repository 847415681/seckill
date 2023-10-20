package com.example.demo.controller;

import com.example.demo.dao.UserDao;
import com.example.demo.service.impl.UserServiceImpl;
import com.example.demo.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    private UserServiceImpl userServiceImpl;

    public String show(){
        return "hello,welcome to register!";
    }

    @PostMapping
    public Integer save(@RequestBody UserDao user){

        return userServiceImpl.save(user);
    }
}
