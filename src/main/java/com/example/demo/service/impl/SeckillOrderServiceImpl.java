package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.dao.SeckillOrderDao;
import com.example.demo.dao.UserDao;
import com.example.demo.mapper.SeckillOrderMapper;
import com.example.demo.service.SeckillOrderSevice;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class SeckillOrderServiceImpl extends ServiceImpl<SeckillOrderMapper ,SeckillOrderDao> implements SeckillOrderSevice  {

    @Autowired
    private SeckillOrderMapper seckillOrderMapper;
    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public Long getResult(UserDao user, Long goodsId) {

        SeckillOrderDao seckillOrder = seckillOrderMapper.selectOne(new QueryWrapper<SeckillOrderDao>().eq("user_id", user.getId()).eq("goods_id", goodsId));

        if (null != seckillOrder) {
            return seckillOrder.getOrderId();
        } else if (redisTemplate.hasKey("isStockEmpty:" + goodsId)) {
            return -1L;
        } else {
            return 0L;
        }

    }
}
