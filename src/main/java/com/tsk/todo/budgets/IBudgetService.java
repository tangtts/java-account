package com.tsk.todo.budgets;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tsk.todo.exception.ResultResponse;
import com.tsk.todo.mapper.BudgetsMapper;
import com.tsk.todo.mapper.RecordsMapper;
import com.tsk.todo.pojo.BudgetsPojo;
import com.tsk.todo.pojo.RecordsPojo;
import com.tsk.todo.pojo.User;
import com.tsk.todo.recodes.ICostOrIncomeService;
import com.tsk.todo.req.AddBudgetReq;
import com.tsk.todo.req.UpdateBudgetReq;

import java.util.HashMap;

/**
 * @author Tsk
 * @date 2024/7/12 0012
 */

public interface IBudgetService extends IService<BudgetsPojo> {
    ResultResponse<String> add(AddBudgetReq addBudgetReq);
    ResultResponse<HashMap<String, Object>> getDetail(String startTime, String endTime);

    ResultResponse<String> update(UpdateBudgetReq updateBudgetReq);
}
