package com.example.demo.utils;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

import java.sql.SQLOutput;

@Component
public class MD5Util {
    public static String md5 (String src){
        return DigestUtils.md5Hex(src);
    }

    private static final String salt = "sweet";

    public static final String inputPasstoFromPass(String inputPass){
        String str = salt.charAt(0)+salt.charAt(1)+inputPass+salt.charAt(3)+salt.charAt(4);

        return md5(str);
    }

    public static void main(String[] args) {
        System.out.println(inputPasstoFromPass("123"));
    }


}
