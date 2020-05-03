package com.lgc.demo1.exception;

/**
 *自定义异常<br>
 *Created by L on  2020/3/11  8:13
 */

public class CustomizeException extends RuntimeException {
    private String message;//异常信息
    private Integer code;//异常码

    public CustomizeException(ICustomizeErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
