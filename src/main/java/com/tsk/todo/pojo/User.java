package com.tsk.todo.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@TableName("users")
public class User extends CommonPojo implements Serializable {
    @TableId()
    private Integer userId;

    private String nickName;

    private String password;

    private String phoneNumber;
//    头像
    private String avatar;
}
