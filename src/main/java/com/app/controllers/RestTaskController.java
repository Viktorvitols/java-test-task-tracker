package com.app.controllers;

import com.app.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestTaskController {

    @Autowired
    TaskService taskService;

    @PostMapping("/restSetAssignee")
    public void restSetAssignee(int taskId, int userId) {
        taskService.changeAssignee(taskId, userId);
    }
}
