package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.dao.SeckillOrderDao;
import com.example.demo.dao.UserDao;


public interface SeckillOrderSevice extends IService<SeckillOrderDao>  {
     Long getResult(UserDao user, Long goodsId);
}
