package com.cako.vo;

import com.cako.enums.ExceptionEnum;
import lombok.Data;

@Data
public class ExceptionResult {
    private int status;
    private String msg;
    private Long timestamp;
    public ExceptionResult(ExceptionEnum em){
        this.status=em.getCode();
        this.msg=em.getMsg();
        this.timestamp=System.currentTimeMillis();
    }
}
