package com.tsk.todo.todo.Group;

import com.tsk.todo.exception.ResultResponse;
import com.tsk.todo.pojo.Todo;
import com.tsk.todo.req.TodoGroupReq;
import com.tsk.todo.util.TodoGroupWithCount;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author Tsk
 * @date 2024/6/26 0026
 */
@RestController
@RequestMapping("/group")
public class GroupController {

    @Resource
    GroupService groupService;

    @GetMapping("/getGroup")
    public ResultResponse<List<Map<String, Object>>> getGroup(@RequestParam String groupName) {
        return groupService.getGroup(groupName);
    }

    @PostMapping("/addGroup")
    public ResultResponse<String> addGroup(@Validated @RequestBody TodoGroupReq todoGroup) {
        return groupService.addGroup(todoGroup);
    }

    @GetMapping("/getAllComplete")
    public ResultResponse<List<Todo>> getAllComplete() {
        return groupService.getAllComplete();
    }
    @GetMapping("/getAllMarked")
    public ResultResponse<List<Todo>> getAllMarked() {
        return groupService.getAllMarked();
    }

    @GetMapping("/deleteGroup")
    ResultResponse<String> deleteTodo(@RequestParam Integer groupId) {
        return groupService.deleteGroup(groupId);
    }
}
