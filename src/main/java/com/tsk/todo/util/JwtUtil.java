package com.tsk.todo.util;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTHeader;
import cn.hutool.jwt.JWTUtil;
import com.tsk.todo.req.LoginReq;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
/**
 * @author Tsk
 * @date 2024/6/26 0026
 */
public class JwtUtil {
    static private final String JWT_SECRET = "1234";

    public static String getUUID(){

        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 生成jtw
     * @param subject token中要存放的数据（json格式）
     * @return
     */


   static public String encodeJwt(LoginReq loginReq) {
      return JWT.create()
               .setPayload("phoneNumber", loginReq.getPhoneNumber())
               .setPayload("expire_time", System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 15)
               .setKey(JWT_SECRET.getBytes())
               .sign();
    }

   static public Object decodeJwt(String token) {
        if (JWT.of(token).setKey(JWT_SECRET.getBytes()).validate(0)) {
            JWT jwt = JWT.of(token);
            jwt.getHeader(JWTHeader.TYPE);
           return jwt.getPayload("phoneNumber");
        }
        throw new RuntimeException();
    }

     public static void main(String[] args){






         System.out.println(123);
         System.out.println(234);

     };
    static public boolean verifyJwt(String token) {
        return JWTUtil.verify(token, JWT_SECRET.getBytes());
    }
}
