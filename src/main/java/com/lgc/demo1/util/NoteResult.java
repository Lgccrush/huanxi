package com.lgc.demo1.util;

import lombok.Data;

import java.io.Serializable;

/**
 * 用于接收返回结果的类
 *
 * @author L
 */
@Data
public class NoteResult<T> implements Serializable {
    private int type;//状态
    private String message;//提示消息
    private T data;//返回类型(不知道具体类型用泛型)
}
