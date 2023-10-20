package com.example.demo.service;

import com.example.demo.vo.GoodsVo;

import java.util.List;

public interface GoodsService {

    GoodsVo findGoodsVoBygoodsId(Long goodsId);

    List<GoodsVo> findGoodsvo();
}
