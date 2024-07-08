package com.tsk.todo.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Tsk
 * @date 2024/6/26 0026
 */
@Getter
@Setter
@TableName("todoGroup")
public class TodoGroup extends CommonPojo {
    @TableId()
    private Integer groupId;

    private String groupName;
}
