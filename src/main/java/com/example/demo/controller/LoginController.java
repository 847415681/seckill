package com.example.demo.controller;

import com.example.demo.service.UserService;
import com.example.demo.vo.LoginVo;
import com.example.demo.vo.RespBean;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Resource
    private UserService userService;


    @GetMapping
    public String show()
    {
        System.out.println("1");
        return "hello,please login";
    }

    @GetMapping("/toLogin")
    public RespBean tologin(@RequestBody LoginVo loginVo, HttpServletRequest requests, HttpServletResponse response){
        System.out.println(loginVo);
        return userService.toLogin(loginVo,requests,response);
    }
}
