package com.app.controllers;

import com.app.model.Task;
import com.app.services.TaskListService;
import com.app.services.TaskService;
import com.app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class TaskListController {

    @Autowired
    private TaskListService taskListService;

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @GetMapping("/tasklist")
    public String tasklist(@ModelAttribute Task task, Model model, HttpSession session) {
        List<Task> taskList = taskListService.getTaskList();
        model.addAttribute("tasklist", taskList);
        model.addAttribute("username", session.getAttribute("username"));
        model.addAttribute("users", userService.getUserList());
        return "tasklist";
    }

    @RequestMapping("/tasklist")
    public String searchTask(@RequestBody String searchText, Model model, HttpSession session) {
        List<Task> searchResult = taskListService.searchTask(searchText);
        model.addAttribute("tasklist", searchResult);
        model.addAttribute("username", session.getAttribute("username"));
        return "tasklist";
    }
}
