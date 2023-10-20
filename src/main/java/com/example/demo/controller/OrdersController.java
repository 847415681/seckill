package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.dao.OrdersDao;
import com.example.demo.dao.UserDao;
import com.example.demo.service.GoodsService;
import com.example.demo.service.OrderService;
import com.example.demo.vo.GoodsVo;
import com.example.demo.vo.RespBean;
import com.example.demo.vo.RespBeanEnum;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrdersController {
    @Resource
    private GoodsService goodsService;
    @Resource
    private OrderService orderService;

    @RequestMapping("/list")
    public RespBean list(HttpSession session, @CookieValue("userTicket") String ticket) {
        if (ticket.isEmpty()) {
            return RespBean.error(RespBeanEnum.BIND_ERROR);
        }

        UserDao user = (UserDao) session.getAttribute(ticket);

        if (user == null) {
            return RespBean.error(RespBeanEnum.BIND_ERROR);
        }

        return RespBean.success(orderService.list(user.getId()));
    }

    @RequestMapping("/payed/{goodsId}")
    public RespBean seekill(HttpSession session, @CookieValue("userTicket") String ticket,  @PathVariable Long goodsId) {
        if (ticket.isEmpty()) {
            return RespBean.error(RespBeanEnum.BIND_ERROR);
        }

        UserDao user = (UserDao) session.getAttribute(ticket);

        if (user == null) {
            return RespBean.error(RespBeanEnum.BIND_ERROR);
        }

        GoodsVo goods = goodsService.findGoodsVoBygoodsId(goodsId);

        OrdersDao ordersDao = orderService.getOne(new QueryWrapper<OrdersDao>().eq("user_id", user.getId()).eq("goods_id", goods.getId()));

        System.out.println(ordersDao);

        if (ordersDao == null) {
            return RespBean.error(RespBeanEnum.NOTORDER_ERROR);
        }else if (ordersDao.getStatus() != 0){
            return RespBean.error(RespBeanEnum.HAVE_PAYED);
        }else {

            orderService.pay(ordersDao);
            return RespBean.success("支付成功");
        }
    }

    @RequestMapping("/cancel/{goodsId}")
    public RespBean cancelOrder(HttpSession session, @CookieValue("userTicket") String ticket,  @PathVariable("goodsId") Long goodsId) {
        if (ticket.isEmpty()) {
            return RespBean.error(RespBeanEnum.BIND_ERROR);
        }

        UserDao user = (UserDao) session.getAttribute(ticket);

        if (user == null) {
            return RespBean.error(RespBeanEnum.BIND_ERROR);
        }

        GoodsVo goods = goodsService.findGoodsVoBygoodsId(goodsId);

        return orderService.cancellationOrder(user,goods);

    }
}
