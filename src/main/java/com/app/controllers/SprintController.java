package com.app.controllers;

import com.app.model.Sprint;
import com.app.services.SprintService;
import com.app.services.TaskService;
import com.app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;

@Controller
public class SprintController {

    @Autowired
    private SprintService sprintService;

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @GetMapping("/sprint")
    private String sprint(@ModelAttribute Sprint sprint, Model model, HttpSession session) {
        model.addAttribute("username", session.getAttribute("username"));
        model.addAttribute("sprintList", sprintService.getSprintList());
        return "sprint";
    }

    @GetMapping("/sprint/new")
    private String newSprint(@ModelAttribute Sprint sprint, Model model, HttpSession session) {
        model.addAttribute("username", session.getAttribute("username"));
        model.addAttribute("sprint", sprint);
        model.addAttribute("sprintList", sprintService.getSprintList());
        return "create-sprint";
    }

    @GetMapping("/sprint/{sprintId}")
    private String sprint(@PathVariable(value = "sprintId") Integer sprintId,
                          Model model,
                          HttpSession session) throws SQLException {
        model.addAttribute("username", session.getAttribute("username"));
        model.addAttribute("sprintList", sprintService.getSprintList());
        Sprint sprint = sprintService.getSprintById(sprintId);
        model.addAttribute("sprint", sprint);
        model.addAttribute("sprintTasklist", sprintService.getTasksInSprint(sprintId));
        model.addAttribute("tasklist", taskService.getTaskList());
        return "show-sprint";
    }

    @PostMapping("/sprint/new")
    private String createNewSprint(@ModelAttribute Sprint sprint) {
        sprintService.createNewSprint(sprint);
        return "redirect:/success";
    }

    @PostMapping("/sprint/{sprintId}")
    private String deleteSprint(@PathVariable Integer sprintId) {
        if (sprintService.deleteSprint(sprintId)) {
            return "redirect:/sprint";
        } else {
            return "redirect:/invalid";
        }
    }

    @PostMapping("/sprint/{sprintId}/add_task")
    private String addTaskToSprint(@PathVariable Integer sprintId, Integer taskId) {
        sprintService.addTaskToSprint(sprintId, taskId);
        return "redirect:/sprint/{sprintId}";
    }
}