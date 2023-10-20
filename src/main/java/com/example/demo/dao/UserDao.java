package com.example.demo.dao;

import lombok.Data;

@Data
public class UserDao {

    private Long id;
    private String userName;
    private String passWord;
    private int state;
    private String email;
}
