package com.example.demo.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("orders")
public class OrdersDao {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private long userId;
    private Long goodsId;
    private String goodsName;
    private int goodsCount;
    private BigDecimal goodsPrice;
    private int status;
    private Date createDate;
    private Date payDate;
}
