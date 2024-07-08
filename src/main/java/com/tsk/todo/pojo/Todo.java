package com.tsk.todo.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
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
@TableName("todo")
public class Todo extends CommonPojo {
    @TableId()
    private Integer todoId;

    private String todoContent;
    //    关联
    private Integer groupId;

    // 是否完成
    private Integer isCompleted;

    // 是否被标记
    private Integer isMarked;
}
