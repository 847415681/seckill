package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.dao.OrdersDao;
import com.example.demo.dao.SeckillOrderDao;
import com.example.demo.dao.UserDao;
import com.example.demo.service.GoodsService;
import com.example.demo.service.OrderService;
import com.example.demo.service.SeckillOrderSevice;
import com.example.demo.vo.GoodsVo;
import com.example.demo.vo.RespBean;
import com.example.demo.vo.RespBeanEnum;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/seckill")
public class SeckillController {

    @Resource
    GoodsService goodsService;
    @Resource
    SeckillOrderSevice seckillOrderSevice;
    @Resource
    OrderService orderService;

    @RequestMapping("/doseckill")
    public RespBean seekill(HttpSession session, @CookieValue("userTicket") String ticket, @RequestParam("goodsId") Long goodsId){
        if(ticket.isEmpty()){
            return RespBean.error(RespBeanEnum.BIND_ERROR);
        }

        UserDao user = (UserDao) session.getAttribute(ticket);

        if ( user == null){
            return RespBean.error(RespBeanEnum.BIND_ERROR);
        }

        GoodsVo goods = goodsService.findGoodsVoBygoodsId(goodsId);

        if(goods.getStockCount()<1){
            return RespBean.error(RespBeanEnum.EMPTY_ERROR);
        }

        SeckillOrderDao seckillOrderDao = seckillOrderSevice.getOne(new QueryWrapper<SeckillOrderDao>().eq("user_id",user.getId()).eq("goods_id",goodsId));
        if (seckillOrderDao != null)
        {
            return RespBean.error(RespBeanEnum.REPEAT_ERROR);
        }

        Date nowDate = new Date();

        if(nowDate.before(goods.getStartDate())||nowDate.after(goods.getEndDate())){
            return RespBean.error(RespBeanEnum.TIME_ERROR);
        }

        OrdersDao orderDao = orderService.seckill(user,goods);

        System.out.println(orderDao);

        return RespBean.success("秒杀成功咯！");
    }




}
