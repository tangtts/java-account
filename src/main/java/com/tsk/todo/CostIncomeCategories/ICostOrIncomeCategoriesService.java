package com.tsk.todo.CostIncomeCategories;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tsk.todo.exception.ResultResponse;
import com.tsk.todo.pojo.CategoriesPojo;
import com.tsk.todo.pojo.RecordsPojo;
import com.tsk.todo.req.AddCostOrIncomeCategoriesReq;

import java.util.List;
import java.util.Map;

/**
 * @author Tsk
 * @date 2024/7/9 0009
 */
public interface ICostOrIncomeCategoriesService extends IService<CategoriesPojo> {
    ResultResponse<String> add(AddCostOrIncomeCategoriesReq addCostOrIncomeCategoriesReq);

    ResultResponse<List<CategoriesPojo>> getAllCategories(String type);

    ResultResponse<String> delCategory(String id);

    ResultResponse<CategoriesPojo> getCategoryById(String id);
    ResultResponse<String> updateCategory(Integer categoryId,String categoryName);

}
