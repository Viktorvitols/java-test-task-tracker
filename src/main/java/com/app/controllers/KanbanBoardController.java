package com.app.controllers;

import com.app.services.KanbanBoardService;
import com.app.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class KanbanBoardController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private KanbanBoardService kanbanBoardService;

    @GetMapping("/kanban-board")
    public String getKanbanBoard(HttpSession session, Model model) {
        model.addAttribute("username", session.getAttribute("username"));
        model.addAttribute("statuslist", taskService.getTaskStatuses());
        model.addAttribute("tasksByStatus", kanbanBoardService.getTasksByStatus());
        return "kanban-board";
    }

}