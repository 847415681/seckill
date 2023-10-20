package com.example.demo.controller;

import com.example.demo.dao.GoodsDao;
import com.example.demo.dao.UserDao;
import com.example.demo.service.GoodsService;
import com.example.demo.vo.GoodsVo;
import com.example.demo.vo.RespBean;
import com.example.demo.vo.RespBeanEnum;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Resource
    GoodsService goodsService;

    @RequestMapping("/tolist")
    public RespBean list(HttpSession session, @CookieValue("userTicket") String ticket){
        System.out.println(ticket+"------------");

        if(ticket.isEmpty()){
            return RespBean.error(RespBeanEnum.BIND_ERROR);
        }
        // 登录成功 session: uuid -> userId
        // cookies: ticket -> uuid
        // 校验 从cookie中取ticket对应的值(uuid), 然后再从session中取uuid对应的值, userId

        UserDao user = (UserDao) session.getAttribute(ticket);
        // 取不到值, 则没有登录
        if ( user == null){
            return RespBean.error(RespBeanEnum.BIND_ERROR);
        }
//        model.addAttribute("user",user);
//        model.addAttribute("goodslist",goodsService.findGoodsvo());

        return RespBean.success(goodsService.findGoodsvo());
    }

    @RequestMapping("/todetail/{goodsId}")
    public String toDetail( HttpSession session, @CookieValue("userTicket") String ticket, @PathVariable Long goodsId){
        UserDao user = (UserDao) session.getAttribute(ticket);
        if(ticket.isEmpty()){
            return "please to login!";
        }
        //model.addAttribute("user",user);
        GoodsVo gv= goodsService.findGoodsVoBygoodsId(goodsId);

        String pattern = "yyyy-MM-dd"; // 定义日期格式化模板
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        Date startTime = gv.getStartDate();
        Date endTime = gv.getEndDate();

        int status = 0;
        Date nowDate = new Date();
        String[] seckillStatue = {"秒杀未开始","正在秒杀中","秒杀已结束"};
        
        if (nowDate.before(startTime)){

        }
        else if (nowDate.after(endTime)) {
            status = 2;
        }else {
            status = 1;
        }

        return "hello," + user.getUserName() + "\n以下是选择的的秒杀商品：\n"
                +"商品名字："+gv.getGoodsTitle()+"\n"
                +"商品详细："+gv.getGoodsDetail()+"\n"
                +"商品原价："+gv.getGoodsPrice()+"\n"
                +"秒杀价格："+gv.getSeckillPrice()+"\n"
                +"库存数量："+gv.getStockCount()+"\n"
                +"开始时间："+dateFormat.format(startTime)+"\n"
                +"结束时间："+dateFormat.format(endTime)+"\n"
                +"现在"+seckillStatue[status];
    }


}
