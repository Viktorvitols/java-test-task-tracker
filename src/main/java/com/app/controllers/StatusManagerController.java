package com.app.controllers;

import com.app.model.Status;
import com.app.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class StatusManagerController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/status-manager")
    public String getStatusManager(Model model, HttpSession session) {
        List<Status> statusList = taskService.getTaskStatuses();
        model.addAttribute("statuses", statusList);
        model.addAttribute("username", session.getAttribute("username"));
        return "status-manager";
    }


    @PostMapping("/add-status")
    public String addNewAfterStatus(String newStatus, String afterStatus) {
        if (afterStatus.isEmpty()) {
            taskService.addNewStatus(newStatus);
        } else {
            taskService.addNewAfterStatus(newStatus, afterStatus);
        }
        return "redirect:/status-manager";
    }

    @PostMapping("/remove-status")
    public String removeStatus(String oldStatus) {
        taskService.removeStatus(oldStatus);
        return "redirect:/status-manager";
    }

}
