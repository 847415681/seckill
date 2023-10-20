package com.example.demo.service.impl;

import com.example.demo.mapper.GoodsMapper;
import com.example.demo.service.GoodsService;
import com.example.demo.utils.RedisKey;
import com.example.demo.vo.GoodsVo;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Resource
    GoodsMapper goodsMapper;
    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public GoodsVo findGoodsVoBygoodsId(Long goodsId) {
        final String key = "goods:" + goodsId.toString();
        String goodsVoString = (String) redisTemplate.opsForValue().get(key);
        GoodsVo goodsVo = null;
        if (goodsVoString == null) {
            System.out.println("==Readis无缓存==正在查询数据库==");
            goodsVo = goodsMapper.findGoodsVoByGoodsId(goodsId);
            try {
                String value = RedisKey.goodsVoToJson(goodsVo);
                redisTemplate.opsForValue().set(key, value, 1, TimeUnit.MINUTES);
            } catch (Exception e) {
                System.out.println("redis存储goodsbyid失败");
            }
            return goodsVo;
        } else {
            try {
                System.out.println("==Readis有缓存=从缓存中提取=");
                goodsVo = RedisKey.goodsVoFromJson(goodsVoString);
                return goodsVo;
            } catch (Exception e) {
                System.out.println("提取失败");
            }

        }
        return goodsVo;
    }

    @Override
    public List<GoodsVo> findGoodsvo () {
        List<GoodsVo> goodsVoList = null;
        String key = "goods_list";
        List<String> goodsList = redisTemplate.opsForList().range(key,1,-1);
        System.out.println(  goodsList.size() );
        if (goodsList.size()==0){
            System.out.println("==Readis无缓存==正在查询数据库==");
            try{
                goodsVoList = goodsMapper.findGoodsVo();
                for(GoodsVo goodsVo : goodsVoList){
                    String goodsJson = RedisKey.goodsVoToJson(goodsVo);
                    redisTemplate.opsForList().rightPush(key,goodsJson);
                }
                return goodsVoList;
            }catch (Exception e){
                System.out.println("redis存储goodsList失败");
            }
        }else {
            System.out.println("==Readis有缓存=正在缓存中提取=");
            try {

            for(String goodlist : goodsList){
                GoodsVo goodsVo = RedisKey.goodsVoFromJson(goodlist);
                goodsVoList.add(goodsVo);
            }
            return goodsVoList;
        }catch (Exception e){
                System.out.println("提取失败");
            }
        }

        return goodsVoList;
    }

}
