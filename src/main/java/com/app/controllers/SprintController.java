package com.app.controllers;

import com.app.model.Sprint;
import com.app.model.Task;
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
import java.util.ArrayList;
import java.util.List;

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

    @GetMapping("/sprint/{sprintId}")
    private String sprint(@PathVariable(value = "sprintId") Integer sprintId,
                          Model model,
                          HttpSession session) throws SQLException {
        model.addAttribute("username", session.getAttribute("username"));
        Sprint sprint = sprintService.getSprintById(sprintId);
        model.addAttribute("sprint", sprint);
        List<Integer> taskIdList = sprint.getTasklist();
        List<Task> taskList = new ArrayList<>();
        for (int id : taskIdList) {
            taskList.add(taskService.getTaskById(id));
        }
        model.addAttribute("tasklist", taskList);
        return "sprint";
    }


}
