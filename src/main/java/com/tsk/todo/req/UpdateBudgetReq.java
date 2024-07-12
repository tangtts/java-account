package com.tsk.todo.req;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author Tsk
 * @date 2024/7/9 0009
 */
@Data
public class UpdateBudgetReq {
    @NotBlank(message = "预算id不能为空")
    private String budgetId;

    @Min(value = 0, message = "金额不能小于0")
    @NotBlank(message = "金额不能为空")
    private float count;

    @NotBlank(message = "开始时间")
    private String startTime;

    @NotBlank(message = "结束时间")
    private String endTime;
}
