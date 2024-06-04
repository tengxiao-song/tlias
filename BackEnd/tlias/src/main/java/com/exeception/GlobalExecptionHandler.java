package com.exeception;

import com.pojo.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExecptionHandler {

    @ExceptionHandler(Exception.class) // handle all exceptions in springboot
    public Result ex(Exception e) {
        return Result.error("操作失败");
    }
}
