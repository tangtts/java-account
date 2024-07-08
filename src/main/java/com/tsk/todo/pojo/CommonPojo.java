package com.tsk.todo.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.util.validation.metadata.DatabaseException;
import org.apache.ibatis.reflection.MetaObject;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author Tsk
 * @date 2024/6/25 0025
 */
@Data
public class CommonPojo implements Serializable {
    private LocalDateTime updateTime;

    private LocalDateTime createTime;

    @TableLogic
    private Integer isDeleted;
}
