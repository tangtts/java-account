package com.tsk.todo.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Tsk
 * @date 2024/7/9 0009
 */
@Getter
@Setter
@TableName("recodes")
public class RecordsPojo extends CommonPojo implements Serializable {
    @TableId
    private Integer recodeId;
    private Integer userId;

    //    消费或者支出
    private Integer incomeOrExpand;

    private Float money;

    private String remark;
    //    消费时间，不一定是创建时间
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time;

    //    支付方式
    private Integer payAccount;

    private List<String> picUrls;

}
