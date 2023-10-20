package com.example.demo.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("seckill_order")
public class SeckillOrderDao {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long userId;
    private Long orderId;
    private Long goodsId;
}
