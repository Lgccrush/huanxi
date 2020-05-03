package com.lgc.demo1.util;

import org.apache.tomcat.util.codec.binary.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class NoteUtil {

    /**
     * 将传入的src加密处理
     *
     * @param src：明文字符串
     * @return 返回加密后的字符串结果
     * @throws NoSuchAlgorithmException
     */
    public static String md5(String src) {
        /*
         * 将字符串信息采用MD5处理
         */
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] output = md.digest(src.getBytes());
            /*
             * 将MD5处理结果利用Base64转成字符串
             */
            String str = Base64.encodeBase64String(output);
            return str;
        } catch (Exception e) {
            throw new NoteException("加密异常", e);
        }
    }

    /*
     * 利用UUID算法生成主键
     */
    public static String createId() {
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();

        return id.replace("-", "");
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        System.out.println(md5("123456"));
//		System.out.println(md5("456"));
        System.out.println(createId());
        System.out.println(md5(createId()));

    }
}
