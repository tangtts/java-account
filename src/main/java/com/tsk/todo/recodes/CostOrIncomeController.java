package com.tsk.todo.recodes;

import com.tsk.todo.exception.ResultResponse;
import com.tsk.todo.pojo.RecordsPojo;
import com.tsk.todo.req.AddCostOrIncomeReq;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Tsk
 * @date 2024/7/9 0009
 */
@RestController
@RequestMapping("costOrIncome")
public class CostOrIncomeController {

    @Resource
    private ICostOrIncomeService costOrIncomeService;

    @PostMapping("/add")
    public ResultResponse<String> add(@RequestBody AddCostOrIncomeReq addCostOrIncomeReq) {
        return costOrIncomeService.add(addCostOrIncomeReq);
    }

    @GetMapping("/list")
    public ResultResponse<List<RecordsPojo>> getAllRecords(
            @RequestParam("type") String type,
            @RequestParam("startTime") String startTime,
            @RequestParam("endTime") String endTime
    ) {
        return costOrIncomeService.getAllRecords(type, startTime, endTime);
    }

    @GetMapping("/detail")
    public ResultResponse<RecordsPojo> getRecordById(
            @RequestParam("recordId") String recordId
    ) {
        return costOrIncomeService.getRecordById(recordId);
    }


// 获取首页统计数据
    @GetMapping("/listSep")
    public ResultResponse<Map<String, Double>> getAllRecordsSeparate() {
        return costOrIncomeService.getAllRecordsSeparate();
    }
}
