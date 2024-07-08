package com.tsk.todo.todo.Todo;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tsk.todo.exception.ResultResponse;
import com.tsk.todo.mapper.TodoMapper;
import com.tsk.todo.pojo.Todo;
import com.tsk.todo.pojo.TodoGroup;
import com.tsk.todo.req.TodoReq;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Tsk
 * @date 2024/6/27 0027
 */
@Service
public class TodoService {

    @Resource
    TodoMapper todoMapper;

    public ResultResponse<String> addTodo(TodoReq todoReq) {
        Todo todo = new Todo();
        todo.setTodoContent(todoReq.getTodoContent());
        todo.setGroupId(todoReq.getGroupId());
        int insert = todoMapper.insert(todo);
        return ResultResponse.success("添加成功");
    }

    public ResultResponse<List<Todo>> getTodo(Integer groupId) {
        LambdaQueryWrapper<Todo> todoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        todoLambdaQueryWrapper.eq(Todo::getGroupId, groupId);
        List<Todo> todos = todoMapper.selectList(todoLambdaQueryWrapper);
        return ResultResponse.success(todos);
    }

    public ResultResponse<String> updateTodo(TodoReq todoReq) {
        // 根据 todoid 先找到todo
        Todo todo = todoMapper.selectById(todoReq.getTodoId());
        if (ObjectUtil.isNull(todo)) {
            throw new RuntimeException("todo不存在");
        }
        todo.setTodoContent(todoReq.getTodoContent());
        int update = todoMapper.updateById(todo);
        return ResultResponse.success("更新成功");
    }

    public ResultResponse<String> deleteTodo(Integer todoId) {
        Todo todo = todoMapper.selectById(todoId);
        if (ObjectUtil.isNull(todo)) {
            throw new RuntimeException("todo不存在");
        }
        todo.setIsDeleted(1);
        int i = todoMapper.deleteById(todo);
        return ResultResponse.success("删除成功");
    }

    public ResultResponse<String> toggleStatus(Integer todoId, Integer status) {
        Todo todo = todoMapper.selectById(todoId);
        if (ObjectUtil.isNull(todo)) {
            throw new RuntimeException("todo不存在");
        }
        if (status == 1) {
            if (todo.getIsCompleted() == 0) {
                todo.setIsCompleted(1);
            } else {
                todo.setIsCompleted(0);
            }
        } else if (status == 2) {
            if (todo.getIsMarked() == 0) {
                todo.setIsMarked(1);
            } else {
                todo.setIsMarked(0);
            }
        }
        todoMapper.updateById(todo);
        return ResultResponse.success("更新成功");
    }
}
