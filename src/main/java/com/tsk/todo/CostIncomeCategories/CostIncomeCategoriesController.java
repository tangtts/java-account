package com.tsk.todo.CostIncomeCategories;

import com.tsk.todo.exception.ResultResponse;
import com.tsk.todo.pojo.CategoriesPojo;
import com.tsk.todo.req.AddCostOrIncomeCategoriesReq;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Tsk
 * @date 2024/7/10 0010
 */
@RestController
@RequestMapping("costIncomeCategories")
@CrossOrigin("*")
public class CostIncomeCategoriesController {

    @Resource
    private ICostOrIncomeCategoriesService costOrIncomeCategoriesService;

    @PostMapping("/add")
    public ResultResponse<String> add(@RequestBody AddCostOrIncomeCategoriesReq addCostOrIncomeCategoriesReq) {
        return costOrIncomeCategoriesService.add(addCostOrIncomeCategoriesReq);
    }

    @GetMapping("/list")
    public ResultResponse<List<CategoriesPojo>> getAllCategories(@RequestParam("type") String type) {
        return costOrIncomeCategoriesService.getAllCategories(type);
    }

    @GetMapping("/del")
    public ResultResponse<String> delCategory(@RequestParam("id") String id) {
        return costOrIncomeCategoriesService.delCategory(id);
    }

    @GetMapping("update")
    public ResultResponse<String> updateCategory(
            @RequestParam("categoryId") Integer categoryId,
            @RequestParam("categoryName") String categoryName
    ) {
        return costOrIncomeCategoriesService.updateCategory(categoryId, categoryName);
    }
}
