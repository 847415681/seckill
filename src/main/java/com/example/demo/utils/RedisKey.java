package com.example.demo.utils;

import com.example.demo.vo.GoodsVo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class RedisKey {
    public static String goodsVoToJson (GoodsVo goods)throws Exception  {
//        String key;
//        key = "goods:"+goods.getId()+
//                ":"+ goods.getGoodsName()+
//                ":"+ goods.getGoodsTitle()+
//                ":"+ goods.getGoodsImg()+
//                ":"+ goods.getGoodsDetail()+
//                ":"+ goods.getGoodsPrice()+
//                ":"+ goods.getGoodsStock()+
//                ":"+ goods.getStockCount()+
//                ":"+ goods.getSeckillPrice()+
//                ":"+ goods.getStartDate()+
//                ":"+ goods.getEndDate();
//        return key;
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(goods);
    }

    public static GoodsVo goodsVoFromJson (String json)throws  Exception {
        return  new ObjectMapper().readValue(json, GoodsVo.class);
    }
}
