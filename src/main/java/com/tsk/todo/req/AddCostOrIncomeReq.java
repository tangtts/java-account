package com.tsk.todo.req;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Tsk
 * @date 2024/7/9 0009
 */
@Data
public class AddCostOrIncomeReq {
    //    消费或者支出
    @NotBlank(message = "消费类型不能为空")
    private Integer incomeOrExpand;

    @Min(value = 0, message = "金额不能小于0")
    @NotBlank(message = "金额不能为空")
    private Float money;

    @NotBlank(message = "支付方式不能为空")
    private Integer payAccount;

    @NotBlank(message = "分类不能为空")
    private Integer categoryId;

    @NotBlank(message = "消费时间不能为空")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time;

    private List<String> picUrls;

    private String remark;
}
