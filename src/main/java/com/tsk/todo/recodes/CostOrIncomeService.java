package com.tsk.todo.recodes;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tsk.todo.exception.ResultResponse;
import com.tsk.todo.mapper.RecordsMapper;
import com.tsk.todo.mapper.UserMapper;
import com.tsk.todo.pojo.RecordsPojo;
import com.tsk.todo.pojo.User;
import com.tsk.todo.req.AddCostOrIncomeReq;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Tsk
 * @date 2024/7/9 0009
 */
@Service
public class CostOrIncomeService extends ServiceImpl<RecordsMapper, RecordsPojo> implements ICostOrIncomeService {

   public ResultResponse<String> add(AddCostOrIncomeReq addCostOrIncomeReq ){
       RecordsPojo recordsPojo = new RecordsPojo();
       recordsPojo.setIncomeOrExpand(addCostOrIncomeReq.getIncomeOrExpand());
       recordsPojo.setMoney(addCostOrIncomeReq.getMoney());
       recordsPojo.setPayAccount(addCostOrIncomeReq.getPayAccount());
       recordsPojo.setRemark(addCostOrIncomeReq.getRemark());
       recordsPojo.setTime(addCostOrIncomeReq.getTime());
       recordsPojo.setUserId(1);
       boolean save = save(recordsPojo);
       return ResultResponse.success("添加成功:{}");
   }

}
