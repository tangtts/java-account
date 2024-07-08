package com.tsk.todo.todo.Group;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tsk.todo.exception.ResultResponse;
import com.tsk.todo.mapper.GroupMapper;
import com.tsk.todo.mapper.TodoMapper;
import com.tsk.todo.pojo.Todo;
import com.tsk.todo.pojo.TodoGroup;
import com.tsk.todo.req.TodoGroupReq;
import com.tsk.todo.todo.Todo.TodoService;
import com.tsk.todo.util.TodoGroupWithCount;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

/**
 * @author Tsk
 * @date 2024/6/26 0026
 */
@Service
public class GroupService {

    @Resource
    GroupMapper groupMapper;

    @Resource
    TodoMapper todoMapper;

    public ResultResponse<List<Map<String, Object>>> getGroup(String groupName) {
        List<Map<String, Object>> result = new ArrayList<>();
        LambdaQueryWrapper<TodoGroup> todoGroupLambdaQueryWrapper = new LambdaQueryWrapper<>();
        todoGroupLambdaQueryWrapper.like(StrUtil.isNotBlank(groupName),TodoGroup::getGroupName, groupName);

        List<TodoGroup> todoGroups = groupMapper.selectList(todoGroupLambdaQueryWrapper);
        todoGroups.forEach(todoGroup -> {
            Integer count = todoMapper.selectByGroupIdAndIsNotDeleted(todoGroup.getGroupId()).size();
            Map<String, Object> map = new HashMap<>();
            map.put("groupId", todoGroup.getGroupId());
            map.put("groupName", todoGroup.getGroupName());
            map.put("count", count);
           result.add(map);
        });
        return ResultResponse.success(result);
    }

    public ResultResponse<String> addGroup(TodoGroupReq req){
        TodoGroup todoGroup = new TodoGroup();
        todoGroup.setGroupName(req.getGroupName());
        int group = groupMapper.insert(todoGroup);
        return ResultResponse.success("新增成功");
    }

    public ResultResponse<List<Todo>> getAllComplete() {
        List<Todo> result = new ArrayList<>();
        List<TodoGroup> todoGroups = groupMapper.selectList(null);
        todoGroups.forEach(todoGroup -> {
            result.addAll(todoMapper.selectByGroupIdAndIsCompleted(todoGroup.getGroupId()));
        });
        return ResultResponse.success(result);
    }

    public ResultResponse<String> deleteGroup(Integer groupId) {
        TodoGroup group = groupMapper.selectById(groupId);

        if(ObjectUtil.isNull(group)){
            throw new RuntimeException("group不存在");
        }
        group.setIsDeleted(1);
        int i = groupMapper.deleteById(group);
        return ResultResponse.success("删除成功");
    }

    public ResultResponse<List<Todo>> getAllMarked() {
        List<Todo> result = new ArrayList<>();
        List<TodoGroup> todoGroups = groupMapper.selectList(null);
        todoGroups.forEach(todoGroup -> {
            result.addAll(todoMapper.selectByGroupIdAndIsMarked(todoGroup.getGroupId()));
        });
        return ResultResponse.success(result);
    }
}
