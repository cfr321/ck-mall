package com.cako.exception;

import com.cako.enums.ExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CkException extends RuntimeException {
    private ExceptionEnum exceptionEnum;
}
