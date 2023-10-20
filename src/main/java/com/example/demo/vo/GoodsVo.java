package com.example.demo.vo;

import com.example.demo.dao.GoodsDao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data

public class GoodsVo extends GoodsDao {
    private Integer stockCount;
    private BigDecimal seckillPrice;
    private Date startDate;
    private Date endDate;

}
