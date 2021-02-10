package com.app.controllers;

import com.app.model.Task;
import com.app.services.TaskService;
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

    @GetMapping("/")
    public String indexPage() {
        return "index";
    }

    @GetMapping("/taskform")
    public String getTaskForm(Model model) {
        model.addAttribute("taskform", new Task());
        return "taskform";
    }

    @GetMapping("/edittask/{taskId}")
    public String getTaskById(@PathVariable(value = "taskId") Integer id, Model model) throws SQLException {

        model.addAttribute("task", taskService.getTaskById(id));
        return "edittask";
    }

    @GetMapping("/search")
    public String getSearchResults(@ModelAttribute List<Task> taskList, Model model) {
        model.addAttribute("tasklist", taskList);
        return "search";
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

    @PostMapping("/edittask/{taskId}/post")
    public String updateTask(@ModelAttribute Task task, Model model) throws SQLException {
        model.addAttribute("task", task);
        if (taskService.validateTaskData(task) == true) {
            taskService.updateTask(task);
            return "redirect:/success";
        }
        return "redirect:/invalid";
    }

    @RequestMapping("/")
    public String searchTask(@RequestBody String varSummary, Model model) {
        List<Task> searchResult = taskService.searchTask(varSummary);
        model.addAttribute("searchList", searchResult);
        return "/search";
    }
}
