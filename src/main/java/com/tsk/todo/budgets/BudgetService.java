package com.tsk.todo.budgets;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tsk.todo.exception.ResultResponse;
import com.tsk.todo.mapper.BudgetsMapper;
import com.tsk.todo.mapper.RecordsMapper;
import com.tsk.todo.pojo.BudgetsPojo;
import com.tsk.todo.pojo.RecordsPojo;
import com.tsk.todo.recodes.ICostOrIncomeService;
import com.tsk.todo.req.AddBudgetReq;
import com.tsk.todo.req.UpdateBudgetReq;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Tsk
 * @date 2024/7/12 0012
 */
@Service
public class BudgetService extends ServiceImpl<BudgetsMapper, BudgetsPojo> implements IBudgetService {

    @Resource
    private ICostOrIncomeService costOrIncomeService;

    @Resource
    private BudgetsMapper budgetsMapper;

    private final long oneDayTimestamp = 24 * 60 * 60 * 1000;


    @Override
    public ResultResponse<String> add(AddBudgetReq addBudgetReq) {
        BudgetsPojo budgetsPojo = new BudgetsPojo();
        budgetsPojo.setCount(addBudgetReq.getCount());
        budgetsPojo.setStartTime(this.formatStrToTimestamp(addBudgetReq.getStartTime()));
        budgetsPojo.setEndTime(this.formatStrToTimestamp(addBudgetReq.getEndTime()) + oneDayTimestamp);
//        todo userId
        budgetsPojo.setUserId(1);
        save(budgetsPojo);
        return null;
    }

    public ResultResponse<HashMap<String, Object>> getDetail(String startTime, String endTime) {
        Long startTimestamp = this.formatStrToTimestamp(startTime);
        Long endTimestamp = this.formatStrToTimestamp(endTime) + oneDayTimestamp;
        // 1720972800000 1720972800000
        System.out.println("startTime:{}" + startTimestamp);
        System.out.println("endTime:{}"+endTimestamp);
        LambdaQueryWrapper<BudgetsPojo> pojoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        pojoLambdaQueryWrapper.allEq(
                Map.of(
                        BudgetsPojo::getStartTime, startTimestamp,
                        BudgetsPojo::getEndTime, endTimestamp,
                        BudgetsPojo::getUserId, 1
                )
        );

        Map<String, Float> map = costOrIncomeService.getCostAndIncome(startTimestamp, endTimestamp);
        BudgetsPojo one = getOne(pojoLambdaQueryWrapper);
        HashMap<String, Object> objectObjectHashMap = new HashMap<>();


        if (ObjectUtil.isNotNull(one)) {
            objectObjectHashMap.put("count", one.getCount());
            objectObjectHashMap.put("cost", map.get("cost"));
            objectObjectHashMap.put("income", map.get("income"));
            objectObjectHashMap.put("budgetId", one.getBudgetId());
            return ResultResponse.success(objectObjectHashMap);
        } else {
            BudgetsPojo budgetsPojo = new BudgetsPojo();
            objectObjectHashMap.put("count", 0f);
            objectObjectHashMap.put("cost", map.get("cost"));
            objectObjectHashMap.put("income", map.get("income"));

            budgetsPojo.setCount(0f);
            budgetsPojo.setStartTime(startTimestamp);
            budgetsPojo.setEndTime(endTimestamp + oneDayTimestamp);
            budgetsPojo.setUserId(1);
            Integer budgetId = budgetsMapper.insertWithId(budgetsPojo);
            budgetsPojo.setBudgetId(budgetId);
            objectObjectHashMap.put("budgetId", budgetId);
            return ResultResponse.success(objectObjectHashMap);
        }
    }

    @Override
    public ResultResponse<String> update(UpdateBudgetReq updateBudgetReq) {
        UpdateWrapper<BudgetsPojo> pojoUpdateWrapper = new UpdateWrapper<>();
        pojoUpdateWrapper.eq("budget_id", updateBudgetReq.getBudgetId())
                .set("count", updateBudgetReq.getCount());
        boolean update = update(pojoUpdateWrapper);
        return null;
    }

    ;

    private Long formatStrToTimestamp(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        LocalDate localDate = LocalDate.parse(dateString);
        // 将LocalDate转换为Instant，再转换为时间戳
        return localDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
}
