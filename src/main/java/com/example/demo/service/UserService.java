package com.example.demo.service;


import com.example.demo.dao.UserDao;
import com.example.demo.vo.LoginVo;
import com.example.demo.vo.RespBean;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface UserService {

    UserDao getUser();

     RespBean toLogin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response);

    Integer save(UserDao user);

}
