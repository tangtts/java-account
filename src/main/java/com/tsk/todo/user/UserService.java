package com.tsk.todo.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tsk.todo.exception.ResultResponse;
import com.tsk.todo.exception.StatusEnum;
import com.tsk.todo.mapper.UserMapper;
import com.tsk.todo.pojo.User;
import com.tsk.todo.req.LoginReq;
import com.tsk.todo.req.RegisterReq;
import com.tsk.todo.util.JwtUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService extends ServiceImpl<UserMapper, User> implements IUserService {
    @Resource
    UserMapper userMapper;

    @Override
    public  ResultResponse<String> register(RegisterReq user) {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone_number", user.getPhoneNumber());
        boolean isUserExists = userMapper.exists(queryWrapper);

        if (isUserExists) {
            return ResultResponse.error(StatusEnum.SUCCESS, "用户已存在");
        }

        User  u = new User();
        u.setNickName(user.getNickName());
        u.setPassword(user.getPassword());
        u.setPhoneNumber(user.getPhoneNumber());

        int insert = userMapper.insert(u);

        if (insert == 1) {
            return ResultResponse.success("注册成功");
        }
        return ResultResponse.error(StatusEnum.SERVICE_ERROR);
    }

    @Override
    public ResultResponse<String> login(LoginReq loginReq) {
//    先判断是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone_number", loginReq.getPhoneNumber());
        boolean isExistUser = userMapper.exists(queryWrapper);
        if (isExistUser) {
            String s = JwtUtil.encodeJwt(loginReq);
            return ResultResponse.success(s);
        } else {
            return ResultResponse.error(StatusEnum.UNAUTHORIZED, "用户还未注册");
        }
    }
}
