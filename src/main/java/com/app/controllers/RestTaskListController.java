package com.app.controllers;

import com.app.model.Task;
import com.app.services.TaskListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RestTaskListController {

    @Autowired
    TaskListService taskListService;

    @CrossOrigin
    @GetMapping("/detailedtasklist")
    public List<Task> getDetailedTaskList() {
        return taskListService.getDetailedTaskList();
    }
}
