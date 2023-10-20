package com.example.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum RespBeanEnum {
    //通用
    SUCCESS(200,"SUCCESS"),
    ERROR(500,"服务端异常"),
    //登录
    LOGIN_ERROR(1000,"用户或密码不正确"),
    BIND_ERROR(1001,"校验异常,请检查登录状态"),
    PASSWORD_ERROR(1002, "密码有误"),
    //秒杀
    EMPTY_ERROR(1003,"库存不足"),
    REPEAT_ERROR(1004,"不能重复购买" ),
    NOTORDER_ERROR(1005,"您还未下此订单" ),
    HAVE_PAYED(1005,"您已经买过单了！" ),
    TIME_ERROR(1006, "商品不在秒杀时间内");

    private final Integer code;
    private final String massage;
}
