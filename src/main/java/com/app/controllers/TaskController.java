package com.app.controllers;

import com.app.model.Status;
import com.app.model.Task;
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
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @GetMapping("/taskform")
    public String getTaskForm(Model model, HttpSession session) {
        model.addAttribute("taskform", new Task());
        model.addAttribute("username", session.getAttribute("username"));
        return "taskform";
    }

    @GetMapping("/task/{taskId}")
    public String getTaskById(@PathVariable(value = "taskId") Integer id, Model model, HttpSession session) throws SQLException {
        model.addAttribute("task", taskService.getTaskById(id));
        model.addAttribute("username", session.getAttribute("username"));
        model.addAttribute("commentList", taskService.getTaskCommentList(id));
        return "task";
    }

    @GetMapping("/edittask/{taskId}")
    public String editTaskById(@PathVariable(value = "taskId") Integer id, Model model, HttpSession session) throws SQLException {
        model.addAttribute("task", taskService.getTaskById(id));
        model.addAttribute("users", userService.getUserList());

        Status status = new Status();
        status.setStatus(taskService.getTaskById(id).getStatus());
        List<String> statusList = new ArrayList<String>();
        for (Status statusO : taskService.getTaskStatuses()) {
            statusList.add(statusO.getStatus());
        }
        model.addAttribute("statuses", statusList);
        model.addAttribute("username", session.getAttribute("username"));
        model.addAttribute("commentList", taskService.getTaskCommentList(id));
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

/////////////////////////////////////POST/////////////////////////////////////

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
        return "redirect:/tasklist";
    }

    @PostMapping("/setStatus")
    public String changeStatus(int taskId, String status) {
        taskService.changeStatus(taskId, status);
        return "redirect:/tasklist";
    }

}
