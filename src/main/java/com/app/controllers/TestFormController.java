package com.app.controllers;

import com.app.model.TaskForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TestFormController {

    @GetMapping("/taskform")
    public String getTaskForm(Model model) {
        model.addAttribute("taskform", new TaskForm());
        return "taskform";
    }

    @GetMapping("/success")
    public String getTaskForm() {
        return "success";
    }

    @PostMapping("/taskform")
    public String submitNewTask(@ModelAttribute TaskForm taskForm, Model model) {
        model.addAttribute("taskData", taskForm);
        return "redirect:/success";
    }
}
