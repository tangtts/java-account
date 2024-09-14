package com.tsk.todo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tsk.todo.pojo.BudgetsPojo;
import com.tsk.todo.pojo.RecordsPojo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BudgetsMapper extends BaseMapper<BudgetsPojo> {
    @Insert("insert into budgets(`start_time`,`end_time`,`count`,`user_id`) values(#{startTime},#{endTime},#{count},#{userId})")
    Integer insertWithId(BudgetsPojo budgetsPojo);

    List<RecordsPojo> getListByUserId(Integer loginUserId);
}
