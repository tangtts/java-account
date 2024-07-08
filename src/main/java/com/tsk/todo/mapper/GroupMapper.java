package com.tsk.todo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tsk.todo.pojo.TodoGroup;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Tsk
 * @date 2024/6/26 0026
 */
@Mapper
public interface GroupMapper extends BaseMapper<TodoGroup> {
}
