package com.app.controllers;

import com.app.model.Task;
import com.app.services.TaskService;
import com.app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpSession;

@Controller
public class MenuController {

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @GetMapping("/menu")
    public String menuPage(@ModelAttribute Task task, Model model, HttpSession session) {
        model.addAttribute("username", session.getAttribute("username"));
        Integer userId = userService.getUserByUsername(session.getAttribute("username").toString()).getId();
        model.addAttribute("notifications", taskService.getRecentlyUpdatedTasks(userId));
        model.addAttribute("user", userService.getUserByUsername(session.getAttribute("username").toString()));
        return "menu";
    }
}
