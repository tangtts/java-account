package com.tsk.todo.budgets;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tsk.todo.exception.ResultResponse;
import com.tsk.todo.mapper.RecordsMapper;
import com.tsk.todo.pojo.BudgetsPojo;
import com.tsk.todo.pojo.RecordsPojo;
import com.tsk.todo.req.AddBudgetReq;
import com.tsk.todo.req.AddCostOrIncomeReq;
import com.tsk.todo.req.UpdateBudgetReq;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Tsk
 * @date 2024/7/9 0009
 */
@RestController
@RequestMapping("budget")
@CrossOrigin("*")
public class BudgetsController {

    @Resource
    private IBudgetService iBudgetService;

    @PostMapping("/add")
    public ResultResponse<String> add(@RequestBody AddBudgetReq addBudgetReq) {
        return iBudgetService.add(addBudgetReq);
    }

    @PostMapping("/update")
    public ResultResponse<String> update(@RequestBody UpdateBudgetReq updateBudgetReq) {
        return iBudgetService.update(updateBudgetReq);
    }

    @GetMapping("/detail")
    ResultResponse<HashMap<String, Object>> getDetail(@RequestParam("startTime") String startTime,@RequestParam("endTime") String endTime){
        return iBudgetService.getDetail(startTime, endTime);
    }

//    @GetMapping("/list")
//    public ResultResponse<List<RecordsPojo>> getAllRecords(
//            @RequestParam("type") String type,
//            @RequestParam("startTime") String startTime,
//            @RequestParam("endTime") String endTime
//    ) {
//        return costOrIncomeService.getAllRecords(type, startTime, endTime);
//    }


}
