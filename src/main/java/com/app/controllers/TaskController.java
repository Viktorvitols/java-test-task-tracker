package com.app.controllers;

import com.app.model.Login;
import com.app.model.Task;
import com.app.services.TaskService;
import com.app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@Controller
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @GetMapping("/menu")
    public String menuPage() {
//        model.addAttribute("userMainInfo", userService.loginUser(login));
        return "menu";
    }

    @GetMapping("/taskform")
    public String getTaskForm(Model model) {
        model.addAttribute("taskform", new Task());
        return "taskform";
    }

    @GetMapping("/task/{taskId}")
    public String getTaskById(@PathVariable(value = "taskId") Integer id, Model model) throws SQLException {
        model.addAttribute("task", taskService.getTaskById(id));
        model.addAttribute("users", userService.getUserList());
        return "task";
    }

    @GetMapping("/edittask/{taskId}")
    public String editTaskById(@PathVariable(value = "taskId") Integer id, Model model) throws SQLException {

        model.addAttribute("task", taskService.getTaskById(id));
        return "edittask";
    }

    @GetMapping("/success")
    public String getSuccessForm() {
        return "success";
    }

    @GetMapping("/invalid")
    public String getInvalidForm() {
        return "invalid";
    }


    @PostMapping("/taskform")
    public String submitNewTask(@ModelAttribute Task task, Model model) {
        model.addAttribute("taskform", task);
        if (taskService.validateTaskData(task) == true) {
            taskService.storeTask(task);
            return "redirect:/success";
        }
        return "redirect:/invalid";
    }

    @PostMapping("/edittask/{taskId}")
    public String updateTask(@ModelAttribute Task task, Model model) throws SQLException {
        if (taskService.validateTaskData(task) == true) {
            taskService.updateTask(task);
            return "redirect:/success";
        }
        return "redirect:/invalid";
    }

    @PostMapping("/setAssignee")
    public String changeAssignee(int taskId, int userId) {
        taskService.changeAssignee(taskId, userId);
        return "redirect:/";
    }

}
