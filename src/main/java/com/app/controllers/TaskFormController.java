package com.app.controllers;

import com.app.model.Task;
import com.app.services.TaskFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TaskFormController {

    @Autowired
    private TaskFormService taskFormService;

    @GetMapping("/taskform")
    public String getTaskForm(Model model) {
        model.addAttribute("taskform", new Task());
        return "taskform";
    }

    @GetMapping("/edittask/{taskId}")
    public String getTaskById(@PathVariable(value = "taskId") Integer id) {
        Task task = new Task();
        id = 5;
        task.setId(id);
        return "edittask/";
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
        if (taskFormService.validateTaskData(task) == true) {
            taskFormService.storeTask(task);
            return "redirect:/success";
        }
        return "redirect:/invalid";
    }
}
