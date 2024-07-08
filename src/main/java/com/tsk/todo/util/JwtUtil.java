package com.tsk.todo.util;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTHeader;
import cn.hutool.jwt.JWTUtil;
import com.tsk.todo.req.LoginReq;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Tsk
 * @date 2024/6/26 0026
 */
public class JwtUtil {
    static private final String JWT_SECRET = "1234";

   static public String encodeJwt(LoginReq loginReq) {
        Map<String, Object> map = new HashMap<String, Object>() {
            private static final long serialVersionUID = 1L;

            {
                put("phoneNumber", loginReq.getPhoneNumber());
                put("expire_time", System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 15);
            }
        };
        return JWTUtil.createToken(map, JWT_SECRET.getBytes());
    }

   static public Object decodeJwt(String token) {
        if (JWTUtil.verify(token, JWT_SECRET.getBytes())) {
            final JWT jwt = JWTUtil.parseToken(token);
            jwt.getHeader(JWTHeader.TYPE);
            return jwt.getPayload("sub");
        }
        throw new RuntimeException();
    }
    static public boolean verifyJwt(String token) {
        return JWTUtil.verify(token, JWT_SECRET.getBytes());
    }
}
