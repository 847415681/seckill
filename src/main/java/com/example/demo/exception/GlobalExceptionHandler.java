package com.example.demo.exception;
import com.example.demo.vo.RespBean;
import com.example.demo.vo.RespBeanEnum;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/*全局异常处理*/
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public RespBean Exceptionhandler(Exception e){
        e.printStackTrace();
        if(e instanceof GlobalException){
            GlobalException ex = (GlobalException) e;
            return RespBean.error(ex.getRespBeanEnum());
        }else if (e instanceof BindException){
            BindException ex = (BindException) e;
            RespBean respBean = RespBean.error(RespBeanEnum.BIND_ERROR);
            respBean.setMessage(respBean.getMessage() + ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
            return  respBean;
        }
        return RespBean.error(RespBeanEnum.ERROR);
    }
}
