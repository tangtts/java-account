package com.tsk.todo.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Tsk
 * @date 2024/7/9 0009
 */
@Data
public class AddCostOrIncomeCategoriesReq {
    //    消费或者支出
    @NotBlank(message = "消费类型不能为空")
    private Integer incomeOrExpand;

    @NotBlank(message = "分类名称不能为空")
    private String categoryName;
}
