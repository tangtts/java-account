package com.tsk.todo.util;

import com.tsk.todo.pojo.TodoGroup;

public  class TodoGroupWithCount  extends TodoGroup {
    private TodoGroup todoGroup;
    private Integer count;

    public TodoGroup getTodoGroup() {
        return todoGroup;
    }

    public void setTodoGroup(TodoGroup todoGroup) {
        this.todoGroup = todoGroup;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
    @Override
    public String toString() {
        return "TodoGroupWithCount{" +
                "todoGroup=" + todoGroup +
                ", count=" + count +
                '}';
    }
}