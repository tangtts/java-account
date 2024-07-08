package com.tsk.todo.todo.Todo;

import com.tsk.todo.exception.ResultResponse;
import com.tsk.todo.pojo.Todo;
import com.tsk.todo.req.TodoReq;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Tsk
 * @date 2024/6/27 0027
 */
@RestController
@RequestMapping("/todo")
public class TodoController {

    @Resource
    TodoService todoService;

    @PostMapping("/addTodo")
    ResultResponse<String> addTodo(@Validated(TodoReq.Add.class) @RequestBody TodoReq todoReq){
        return todoService.addTodo(todoReq);
    }

    /**
     *  根据 todoId 获取 todo
     * */
    @GetMapping("/getTodo")
    ResultResponse<List<Todo>> getTodo(@RequestParam Integer groupId){
        return todoService.getTodo(groupId);
    }

    // 切换完成状态 type = 1 是 complete  type =2 是 marked
    @GetMapping("/toggleStatus")
    ResultResponse<String> toggleStatus(@RequestParam Integer todoId, @RequestParam Integer type){
        return todoService.toggleStatus(todoId,type);
    }


//   更新
    @PostMapping("/updateTodo")
    ResultResponse<String> updateTodo(@Validated(TodoReq.Update.class) @RequestBody TodoReq todoReq){
        return todoService.updateTodo(todoReq);
    }

    @GetMapping("/deleteTodo")
    ResultResponse<String> deleteTodo(@RequestParam Integer todoId){
        return todoService.deleteTodo(todoId);
    }
}
