package com.tsk.todo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tsk.todo.pojo.Todo;
import com.tsk.todo.pojo.TodoGroup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author Tsk
 * @date 2024/6/26 0026
 */
@Mapper
public interface TodoMapper extends BaseMapper<Todo> {
    /**
     * 根据分组id查询todo
     *
     * @param groupId 分组id
     * @return todo列表
     */
    @Select("select * from todo where groupId = #{groupId}")
    List<Todo> selectByGroupId(Integer groupId);

    @Select("select * from todo where groupId = #{groupId} AND isCompleted = 1 AND isDeleted = 0")
    List<Todo> selectByGroupIdAndIsCompleted(Integer groupId);

    @Select("select * from todo where groupId = #{groupId} AND isMarked = 1 AND isDeleted = 0")
    List<Todo> selectByGroupIdAndIsMarked(Integer groupId);

    @Select("select * from todo where groupId = #{groupId} AND isDeleted = 0")
    List<Todo> selectByGroupIdAndIsNotDeleted(Integer groupId);
}
