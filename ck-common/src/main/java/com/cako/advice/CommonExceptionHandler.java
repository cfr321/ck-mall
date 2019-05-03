package com.cako.advice;

import com.cako.exception.CkException;
import com.cako.vo.ExceptionResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 拦截所有的Exception，进行异常处理,返回异常出现的结果
 * 1、ResponseEntity封装了状态和返回体。
 * 2、ExceptionResult就是那个返回体
 * 3、CkException自定义的异常处理，继承自RuntimeException
 * 4、定义枚举类，来枚举返回的各种类型，枚举类型基本属性有状态码和状态信息
 */
@ControllerAdvice
public class CommonExceptionHandler {
    @ExceptionHandler(CkException.class)
    public ResponseEntity<ExceptionResult> handleException(CkException e){
        return ResponseEntity.status(e.getExceptionEnum().getCode())
                .body(new ExceptionResult(e.getExceptionEnum()));
    }
}
