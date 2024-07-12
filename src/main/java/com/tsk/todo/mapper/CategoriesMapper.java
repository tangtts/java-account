package com.tsk.todo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tsk.todo.pojo.CategoriesPojo;
import com.tsk.todo.pojo.RecordsPojo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoriesMapper extends BaseMapper<CategoriesPojo> {
}
