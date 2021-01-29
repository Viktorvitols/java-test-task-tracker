package com.app.controllers;

import com.app.model.TaskForm;
import com.app.services.TaskFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TestFormController {

    @Autowired
    private TaskFormService taskFormService;

    @GetMapping("/taskform")
    public String getTaskForm(Model model) {
        model.addAttribute("taskform", new TaskForm());
        return "taskform";
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
    public String submitNewTask(@ModelAttribute TaskForm taskForm, Model model) {
        model.addAttribute("taskform", taskForm);
        if (taskFormService.validateTaskData(taskForm) == true) {
            taskFormService.storeTask(taskForm);
            return "redirect:/success";
        }
        return "redirect:/invalid";
    }
}
