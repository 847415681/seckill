package com.example.demo.controller;

import com.example.demo.dao.OrdersDao;
import com.example.demo.dao.UserDao;
import com.example.demo.mapper.OrderMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import com.example.demo.service.impl.UserServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@SpringBootApplication
@RequestMapping("/test")
public class TestController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Resource
    private UserService userService;

    @Resource
    private OrderMapper oderMapper;

    @RequestMapping("/test")
    public void test() {
        OrdersDao ordersDao = new OrdersDao();
        oderMapper.insert(ordersDao);
        System.out.println(ordersDao);
    }

    @GetMapping
    public List<UserDao> index()
    {
        List<UserDao> all = userMapper.finall();
        return all;
    }

    @PostMapping
    public Integer save(@RequestBody UserDao user){
        return userServiceImpl.save(user);
    }

    @RequestMapping("/a")
    public Map<String, Object> test(@RequestParam("user") String username,
                                    @RequestParam("password") String password,
                                    @RequestParam("email") String email) {
        System.out.println(username + password +email);


        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("data", null);
        result.put("message", "成功");
        return result;
    }

    @RequestMapping("/b")
    public Object test1() {
        return userService.getUser();
    }

    @DeleteMapping("/{id}")
    public Integer delete(@PathVariable Integer id){
        return userMapper.deleteById(id);
    }

    @GetMapping("/page")
    public void page(UserDao user){

    }

    @GetMapping("/Login")
    public UserDao login(@RequestParam Integer id,
                         @RequestParam String password){
        UserDao user = userMapper.login(id);
        if(password.equals(user.getPassWord()))
        {
            return  user;
        }else return null;
    }

}
