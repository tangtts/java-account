package com.tsk.todo.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author Tsk
 * @date 2024/7/9 0009
 */
@Getter
@Setter
@TableName("categories")
public class CategoriesPojo extends CommonPojo implements Serializable {

    @TableId
    private Integer categoryId;

    @JsonIgnore
    private Integer userId;

    //  消费或者支出
    private Integer incomeOrExpand;


    private String categoryName;

}
