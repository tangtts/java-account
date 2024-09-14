package com.tsk.todo.filters;

import cn.hutool.core.convert.ConverterRegistry;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.jwt.Claims;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tsk.todo.config.RedisCache;
import com.tsk.todo.exception.ResultResponse;
import com.tsk.todo.exception.StatusEnum;
import com.tsk.todo.mapper.UserMapper;
import com.tsk.todo.pojo.User;
import com.tsk.todo.user.LoginUser;
import com.tsk.todo.util.JwtUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Map;

/**
 * @author Tsk
 * @date 2024/7/18 0018
 * @description 对每一次请求都会进入该过滤器
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Resource
    RedisCache redisCache;

    @Resource
    UserMapper userMapper;

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        if (!StringUtils.hasText(authorization)) {
            //放行
            filterChain.doFilter(request, response);
            return;
        }
        String token = authorization.replaceFirst("Bearer ", "");
        String requestURI = request.getRequestURI();
        System.out.println("requestURI = " + requestURI);

        if (requestURI.contains("user/login") || requestURI.contains("user/register")) {
            filterChain.doFilter(request, response);
            return;
        }
        String phoneNumber = (String) JwtUtil.decodeJwt(token);

        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getPhoneNumber, phoneNumber);
        User user = userMapper.selectOne(userLambdaQueryWrapper);
        if (ObjUtil.isNull(user)) {
            SecurityContextHolder.clearContext();
            throw new RuntimeException("用户不存在");
        }
//        filterChain.doFilter(request, response);
//        return;
        LoginUser cacheMap = redisCache.getCacheObject("login:" + user.getUserId());
        if (ObjUtil.isNotNull(cacheMap)) {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(cacheMap, null, cacheMap.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        }else  {
            System.out.println("cache Map");
            SecurityContextHolder.clearContext();
        }
        filterChain.doFilter(request, response);
    }
}
