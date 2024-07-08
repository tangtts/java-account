package com.tsk.todo.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author Tsk
 * @date 2024/6/26 0026
 */
@Data
public class TodoReq {

    @NotNull(message = "todoId不能为空",groups = {Update.class})
    private Integer todoId;

    @NotBlank(message = "todo内容不能为空",groups = {Add.class,Update.class})
    private String todoContent;

    @NotNull(message = "分组id不能为空",groups = {Add.class})
    private Integer groupId;

    public interface Update {
    }
    public interface Add {
    }
}
