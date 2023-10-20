package com.example.demo.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("seckill_goods")
public class SeckillGoodsDao {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long goodsId;
    private BigDecimal seckillPrice;
    private int stockCount;
    private Date startDate;
    private Date endDate;
}
