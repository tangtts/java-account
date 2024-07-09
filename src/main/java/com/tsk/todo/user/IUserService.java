package com.tsk.todo.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tsk.todo.exception.ResultResponse;
import com.tsk.todo.pojo.User;
import com.tsk.todo.req.LoginReq;
import com.tsk.todo.req.RegisterReq;

/**
 * @author Tsk
 * @date 2024/7/9 0009
 */
public interface IUserService extends IService<User> {
    ResultResponse<String> register(RegisterReq user);

    ResultResponse<String> login(LoginReq loginReq);
}
