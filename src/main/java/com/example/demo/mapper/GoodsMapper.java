package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.dao.GoodsDao;
import com.example.demo.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface GoodsMapper extends BaseMapper<GoodsDao> {
    @Select("SELECT g.id, g.goods_title as goods_title, g.goods_name, g.goods_img, g.goods_detail,g.goods_price, g.goods_stock, sg.seckill_price,sg.stock_count,sg.start_date, sg.end_date FROM goods g left join seckill_goods sg on g.id = sg.goods_id")
    List<GoodsVo> findGoodsVo();

    @Select("SELECT g.id, g.goods_title as goods_title, g.goods_name, g.goods_img, g.goods_detail,g.goods_price, g.goods_stock, sg.seckill_price,sg.stock_count,sg.start_date, sg.end_date FROM goods g left join seckill_goods sg on g.id = sg.goods_id WHERE g.id = #{id}")
    GoodsVo findGoodsVoByGoodsId(@Param("id") Long id);

}
