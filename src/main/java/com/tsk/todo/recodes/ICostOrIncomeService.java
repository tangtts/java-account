package com.tsk.todo.recodes;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tsk.todo.exception.ResultResponse;
import com.tsk.todo.pojo.RecordsPojo;
import com.tsk.todo.req.AddCostOrIncomeReq;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Tsk
 * @date 2024/7/9 0009
 */
public interface ICostOrIncomeService extends IService<RecordsPojo> {
  ResultResponse<Map<String, Double>> getAllRecordsSeparate();
  ResultResponse<String> add(AddCostOrIncomeReq addCostOrIncomeReq);

  ResultResponse<List<RecordsPojo>> getAllRecords(String type, String startTime, String endTime);
  ResultResponse<RecordsPojo> getRecordById(String recordId);

    Map<String, Float> getCostAndIncome(long startTimestamp, long endTimestamp);
}
