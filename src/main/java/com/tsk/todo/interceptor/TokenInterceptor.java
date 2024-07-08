package com.tsk.todo.interceptor;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.tsk.todo.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;

/**
 * @author Tsk
 * @date 2024/6/27 0027
 */
@Component
public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        String requestURI = request.getRequestURI();
        String[] whiteList = {"/login", "/register"};
        if (ArrayUtil.contains(whiteList, requestURI)
        ) {
            return true;
        }
        if (StrUtil.isBlank(token)) {
            throw new RuntimeException("token不能为空");
        }
        return JwtUtil.verifyJwt(token);
    }
}
