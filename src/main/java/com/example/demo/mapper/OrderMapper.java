package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.dao.OrdersDao;
import com.example.demo.dao.UserDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderMapper extends BaseMapper<OrdersDao> {

    @Select("SELECT * FROM orders WHERE user_id = #{id}")
    public List<OrdersDao> list(@Param("id") Long id);
    @Select("SELECT * FROM orders WHERE user_id = #{userId} AND goods_id = #{goodsId}")
    public OrdersDao find(@Param("userId") Long uId,@Param("goodsId")Long gId);
}
