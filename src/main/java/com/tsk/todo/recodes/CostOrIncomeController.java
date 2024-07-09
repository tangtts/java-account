package com.tsk.todo.recodes;

import com.tsk.todo.exception.ResultResponse;
import com.tsk.todo.req.AddCostOrIncomeReq;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Tsk
 * @date 2024/7/9 0009
 */
@RestController
@RequestMapping("costOrIncome")
public class CostOrIncomeController {

    @Resource
    private CostOrIncomeService costOrIncomeService;
    @PostMapping("/add")
    public ResultResponse<String> add(@RequestBody AddCostOrIncomeReq addCostOrIncomeReq){
        return costOrIncomeService.add(addCostOrIncomeReq);
    }
}
