package com.tsk.todo.util;

import com.tsk.todo.user.LoginUser;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Set;

/**
 * @author Tsk
 * @date 2024/8/9 0009
 */
public class AuthUtils {
    public static LoginUser getLoginUser() {
        return (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static Integer getLoginUserId() {
        return getLoginUser().getUserId();
    }

}
