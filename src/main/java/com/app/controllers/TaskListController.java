package com.app.controllers;

import com.app.model.Task;
import com.app.services.TaskListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TaskListController {

    @Autowired
    private TaskListService taskListService;

    @GetMapping("/tasklist")
    public String tasklist(@ModelAttribute Task task, Model model) {
        List<Task> taskList = taskListService.getTaskList();
        model.addAttribute("tasklist", taskList);
        return "tasklist";
    }

    @RequestMapping("/tasklist")
    public String searchTask(@RequestBody String searchText, Model model) {
        List<Task> searchResult = taskListService.searchTask(searchText);
        model.addAttribute("searchList", searchResult);
        return "/tasklist";
    }
}
