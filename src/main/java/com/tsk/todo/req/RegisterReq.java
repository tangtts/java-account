package com.tsk.todo.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author Tsk
 * @date 2024/6/26 0026
 */
@Data
public class RegisterReq {
    @NotBlank(message = "昵称不能为空")
    private String nickName;

    @NotBlank(message = "手机号不能为空")
    private String phoneNumber;

    @NotBlank(message = "密码不能为空")
    private String password;
}
