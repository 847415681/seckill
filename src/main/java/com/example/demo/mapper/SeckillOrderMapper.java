package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.dao.SeckillOrderDao;
import com.example.demo.vo.RespBean;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SeckillOrderMapper extends BaseMapper<SeckillOrderDao> {
    @Delete("DELETE FROM seckill_order WHERE order_id = #{oderId}")
    public int deleteByOrderId(@Param("oderId") Long id);

}