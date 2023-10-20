package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.dao.OrdersDao;
import com.example.demo.dao.SeckillGoodsDao;
import com.example.demo.dao.SeckillOrderDao;
import com.example.demo.dao.UserDao;
import com.example.demo.mapper.OrderMapper;
import com.example.demo.mapper.SeckillOrderMapper;
import com.example.demo.service.OrderService;
import com.example.demo.service.SeckillGoodsSevice;
import com.example.demo.service.SeckillOrderSevice;
import com.example.demo.vo.GoodsVo;
import com.example.demo.vo.RespBean;
import com.example.demo.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper,OrdersDao> implements OrderService {

    @Autowired
    SeckillGoodsSevice seckillGoodsSevice;

    @Autowired
    OrderMapper orderMapper;
    @Autowired
    SeckillOrderSevice seckillOrderSevice;
    @Autowired
    SeckillOrderMapper seckillOrderMapper;

    @Override
    public OrdersDao seckill(UserDao user, GoodsVo goods) {
        SeckillGoodsDao seckillGoodsDao = seckillGoodsSevice.getOne(new QueryWrapper<SeckillGoodsDao>().eq("goods_id",goods.getId()));
        seckillGoodsDao.setStockCount(seckillGoodsDao.getStockCount()-1);
        seckillGoodsSevice.updateById(seckillGoodsDao);
        //添加订单
        OrdersDao orderDao = new OrdersDao();
        orderDao.setUserId(user.getId());
        orderDao.setGoodsId(goods.getId());
        orderDao.setGoodsName(goods.getGoodsName());
        orderDao.setGoodsCount(1);
        orderDao.setStatus(0);
        orderDao.setGoodsPrice(goods.getSeckillPrice());
        orderDao.setCreateDate(new Date());
        System.out.println("----"+orderDao+"-----");
        orderMapper.insert(orderDao);
        System.out.println(orderDao);
        //添加秒杀订单
        SeckillOrderDao seckillOrderDao = new SeckillOrderDao();
        seckillOrderDao.setGoodsId(orderDao.getGoodsId());
        seckillOrderDao.setUserId(orderDao.getUserId());
        seckillOrderDao.setOrderId(orderDao.getId());
        seckillOrderMapper.insert(seckillOrderDao);
        return orderDao;
    }

    @Override
    public List<OrdersDao> list(Long id) {
        return orderMapper.list(id);
    }

    @Override
    public OrdersDao pay(OrdersDao ordersDao) {
        ordersDao.setStatus(1);

        orderMapper.updateById(ordersDao);
        return ordersDao;
    }

    public RespBean cancellationOrder (UserDao user,GoodsVo goods){
        OrdersDao ordersDao = orderMapper.find(user.getId(),goods.getId());
        if (ordersDao == null){
            return RespBean.error(RespBeanEnum.NOTORDER_ERROR);
        }
        orderMapper.deleteById(ordersDao.getId());
        seckillOrderMapper.deleteByOrderId(ordersDao.getId());

        return RespBean.success("取消成功！");
    }

}
