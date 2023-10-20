package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.dao.OrdersDao;
import com.example.demo.dao.UserDao;
import com.example.demo.vo.GoodsVo;
import com.example.demo.vo.RespBean;

import java.util.List;

public interface OrderService extends IService<OrdersDao> {
    OrdersDao seckill(UserDao user , GoodsVo goods);

    List<OrdersDao>list(Long id);

    OrdersDao pay(OrdersDao ordersDao);
    RespBean cancellationOrder (UserDao user, GoodsVo goods);
}
