package com.tsk.todo.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Tsk
 * @date 2024/7/9 0009
 */
@Getter
@Setter
@TableName("budgets")
public class BudgetsPojo extends CommonPojo implements Serializable {

    @TableId
    private Integer budgetId;

    @JsonIgnore
    private Integer userId;

    //  起始时间
    private long startTime;

//    终止时间
    private long endTime;

//    预算数量
    private float count;

}
