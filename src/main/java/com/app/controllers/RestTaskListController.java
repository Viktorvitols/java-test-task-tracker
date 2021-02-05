package com.app.controllers;

import com.app.model.Task;
import com.app.services.TaskFormService;
import com.app.services.TaskListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RestTaskListController {

    @Autowired
    TaskListService taskListService;

    @Autowired
    TaskFormService taskFormService;

    @CrossOrigin
    @GetMapping("/detailedtasklist")
    public List<Task> getDetailedTaskList() {
        return taskListService.getDetailedTaskList();
    }

    @GetMapping("/detailedtasklist/{project}")
    public List<Task> getFilteredTaskList(@PathVariable(value = "project") String project) {
        return taskListService.getFilteredTaskList(project);
    }

    @PostMapping("/setAssignee")
    public Task changeAssignee(Task task) {
        return taskFormService.changeAssignee();
    }
}
