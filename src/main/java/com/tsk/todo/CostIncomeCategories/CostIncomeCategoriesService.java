package com.tsk.todo.CostIncomeCategories;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tsk.todo.exception.ResultResponse;
import com.tsk.todo.exception.StatusEnum;
import com.tsk.todo.mapper.CategoriesMapper;
import com.tsk.todo.mapper.RecordsMapper;
import com.tsk.todo.pojo.CategoriesPojo;
import com.tsk.todo.pojo.RecordsPojo;
import com.tsk.todo.req.AddCostOrIncomeCategoriesReq;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Tsk
 * @date 2024/7/10 0010
 */
@Service
public class CostIncomeCategoriesService extends ServiceImpl<CategoriesMapper, CategoriesPojo> implements ICostOrIncomeCategoriesService{
    public ResultResponse<String> add(AddCostOrIncomeCategoriesReq addCostOrIncomeCategoriesReq ){
        CategoriesPojo categoriesPojo = new CategoriesPojo();
        categoriesPojo.setCategoryName(addCostOrIncomeCategoriesReq.getCategoryName());
        categoriesPojo.setIncomeOrExpand(addCostOrIncomeCategoriesReq.getIncomeOrExpand());
        categoriesPojo.setUserId(1);
        boolean save = save(categoriesPojo);
        if(save){
            return ResultResponse.success("添加成功");
        }
        return ResultResponse.error(StatusEnum.SQL_ERROR,"添加失败");
    };

    public ResultResponse<List<CategoriesPojo>> getAllCategories(String type){
        LambdaQueryWrapper<CategoriesPojo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(CategoriesPojo::getIncomeOrExpand,type);
        return ResultResponse.success(list(lambdaQueryWrapper));
    }
    public ResultResponse<String> delCategory(String id){
//        不重要的可以直接删除
        boolean b = removeById(id);
        if(b){
            return ResultResponse.success("删除成功");
        }else  {
            return ResultResponse.error(StatusEnum.SQL_ERROR,"删除失败");
        }
    }
    public ResultResponse<CategoriesPojo> getCategoryById(String id){
        CategoriesPojo one = this.getById(id);
        return ResultResponse.success(one);
    }

    public ResultResponse<String> updateCategory(Integer categoryId,String categoryName){
        CategoriesPojo byId = getById(categoryId);
        byId.setCategoryName(categoryName);
        boolean b = updateById(byId);
        return b?ResultResponse.success("修改成功"):ResultResponse.error(StatusEnum.SQL_ERROR,"修改失败");
    }
}
