package com.example.demo.service.impl;

import com.example.demo.dao.UserDao;
import com.example.demo.exception.GlobalException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import com.example.demo.utils.CookieUtil;
import com.example.demo.utils.MD5Util;
import com.example.demo.utils.UUIDUtil;
import com.example.demo.vo.LoginVo;
import com.example.demo.vo.RespBean;
import com.example.demo.vo.RespBeanEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RedisTemplate redisTemplate;



    @Override
    public UserDao getUser() {
        UserDao userDao = userMapper.selectById(100000);
        System.out.println(userDao);
        return userDao;
    }

    @Override
    public RespBean toLogin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response) {
        // 统计耗时
        long start = System.currentTimeMillis();
        System.out.println(start);
        // 登录做示例
        // 首先登录, 先查询redis, redis存储的是键值对, k -> v, 这里存储用户user:user_id
        Integer id = loginVo.getId();
        String password = loginVo.getPassword();
        System.out.println(id +" "+password);
        password = MD5Util.inputPasstoFromPass(password);

        final String key = "user:" + id ;
        String userString = (String) redisTemplate.opsForValue().get(key);
        UserDao user = null;
        if (userString == null) {
            System.out.println("===查询数据库===");
            // redis没有数据, 查询数据库
            user = userMapper.login(id);
            // 查询完成, 将数据库的user存储到redis, 值存储整个UserDAO, 需要将userdao用json序列化成字符串
            try {
                String value = new ObjectMapper().writeValueAsString(user); // 这个可以将对象序列化成string
                redisTemplate.opsForValue().set(key, value, 1, TimeUnit.MINUTES); // 这里有第三个参数, 是设置redis会存储这个string多久
            } catch (Exception e) {
                System.out.println("redis存储失败");
            }
            // 这里存储一分钟
        } else {
            try {
                user = new ObjectMapper().readValue(userString, UserDao.class);
                System.out.println("redis 缓存");
                System.out.println(user);
                System.out.println("end ---");
            } catch (Exception e) {
                System.out.println("将string转对象失败");
            }
        }

        System.out.println("查询耗时: " + (System.currentTimeMillis() - start));

        //输入是否正确
        if( password.isEmpty() || user == null)
        {
            throw new GlobalException(RespBeanEnum.LOGIN_ERROR);
        }
        System.out.println(password);
        if(!password.equals(user.getPassWord()))
        {
            throw new GlobalException(RespBeanEnum.LOGIN_ERROR);
       }

        String ticket = UUIDUtil.uuid();
        request.getSession().setAttribute(ticket,user);
        CookieUtil.setCookie(request,response,"userTicket",ticket);
        System.out.println("------"+ticket);
        return RespBean.success() ;
    }
    @Override
    public Integer save(UserDao user){
        user.setPassWord(MD5Util.inputPasstoFromPass(user.getPassWord()));

        System.out.println(user);

        if(user.getId() == null){
            if (user.getPassWord().isEmpty()){
                throw new GlobalException(RespBeanEnum.PASSWORD_ERROR);
            }
            return userMapper.register(user);
        }else {
            return userMapper.update(user);
        }
    }


}
