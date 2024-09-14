package com.tsk.todo.util;

import com.tsk.todo.exception.ServiceException;
import com.tsk.todo.user.LoginUser;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author Tsk
 * @date 2024/7/25 0025
 */
public class SecurityUtils {
    public static Integer getUserId()
    {
        try
        {
            return getLoginUser().getUserId();
        }
        catch (Exception e)
        {
            throw new RuntimeException("获取用户ID异常");
        }
    }

    /**
     * 获取用户
     **/
    public static LoginUser getLoginUser()
    {
        try
        {
            return (LoginUser) getAuthentication().getPrincipal();
        }
        catch (Exception e)
        {
            throw new RuntimeException("获取用户信息异常");
        }
    }

    public static Authentication getAuthentication()
    {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
