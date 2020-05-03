package com.lgc.demo1.util;

/**
 * 自定义异常
 *
 * @author Administrator
 */
public class NoteException extends RuntimeException {
    public NoteException(String msg, Throwable t) {
        super(msg, t);
    }
}
