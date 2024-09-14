package com.tsk.todo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tsk.todo.pojo.RecordsPojo;
import com.tsk.todo.pojo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RecordsMapper extends BaseMapper<RecordsPojo> {

}
