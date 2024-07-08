package com.tsk.todo.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author Tsk
 * @date 2024/6/26 0026
 */
@Data
public class TodoGroupReq {
    @NotBlank(message = "分组名称不能为空")
    String groupName;
}
