package com.tsk.todo.user;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tsk.todo.config.RedisCache;
import com.tsk.todo.exception.ResultResponse;
import com.tsk.todo.exception.StatusEnum;
import com.tsk.todo.mapper.UserMapper;
import com.tsk.todo.pojo.User;
import com.tsk.todo.req.LoginReq;
import com.tsk.todo.req.RegisterReq;
import com.tsk.todo.util.AuthUtils;
import com.tsk.todo.util.JwtUtil;
import com.tsk.todo.util.SecurityUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class SysLoginService {
    @Resource
    UserMapper userMapper;

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    RedisCache redisCache;

    public ResultResponse<String> register(RegisterReq user) {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone_number", user.getPhoneNumber());
        boolean isUserExists = userMapper.exists(queryWrapper);

        if (isUserExists) {
            return ResultResponse.error(StatusEnum.SUCCESS, "用户已存在");
        }

        User u = new User();
        u.setNickName(user.getNickName());
        u.setPassword(user.getPassword());
        u.setPhoneNumber(user.getPhoneNumber());

        int insert = userMapper.insert(u);

        if (insert == 1) {
            return ResultResponse.success("注册成功");
        }
        return ResultResponse.error(StatusEnum.SERVICE_ERROR);
    }

    public List<User> list() {
        return userMapper.selectList(null);
    }

    public ResultResponse<String> login(LoginReq loginReq) {
        //    先判断是否存在
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhoneNumber, loginReq.getPhoneNumber());
        boolean isExistUser = userMapper.exists(queryWrapper);
        if (!isExistUser) {
            return ResultResponse.error(StatusEnum.UNAUTHORIZED, "用户名或密码错误");
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginReq.getPhoneNumber(), loginReq.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Integer userId = loginUser.getUser().getUserId();
        redisCache.setCacheObject("login:" + userId, loginUser);
        return ResultResponse.success(JwtUtil.encodeJwt(loginReq));
    }

    public ResultResponse<User> getUserDetail() {
        Integer userId = SecurityUtils.getUserId();
        return ResultResponse.success(userMapper.selectById(userId));
    }

    public ResultResponse<String> update(User user) {
        Integer loginUserId = AuthUtils.getLoginUserId();
        int update = userMapper.update(user, new LambdaQueryWrapper<User>().eq(User::getUserId, loginUserId));
        if (update > 0) {
            return ResultResponse.success("更新成功");
        } else {
            return ResultResponse.success("更新失败");
        }
    }
}
