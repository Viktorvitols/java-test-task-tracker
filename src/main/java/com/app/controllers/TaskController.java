package com.app.controllers;

import com.app.model.*;
import com.app.security.CustomUserDetails;
import com.app.services.TaskService;
import com.app.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.sql.Timestamp;
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
        model.addAttribute("users", userService.getUserList());
        return "taskform";
    }

    @GetMapping("/task/{taskId}")
    public String getTaskById(@PathVariable(value = "taskId") Integer id, Model model, HttpSession session) throws SQLException {
        model.addAttribute("task", taskService.getTaskById(id));
        model.addAttribute("username", session.getAttribute("username"));
        model.addAttribute("commentList", taskService.getTaskCommentList(id));
        model.addAttribute("commentCount", taskService.getCommentCount(id));
        model.addAttribute("users", userService.getUserList());
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
        return "edittask";
    }

    @GetMapping("/newComment/{taskId}")
    public String newComment(@PathVariable(value = "taskId") Integer id, Model model, HttpSession session) throws SQLException {
        model.addAttribute("username", session.getAttribute("username"));
        model.addAttribute("task", taskService.getTaskById(id));
        String username = session.getAttribute("username").toString();
        Integer userId = userService.getUserByUsername(username).getId();
        model.addAttribute("userId", userId);
        model.addAttribute("comment", new Comment(id, userId));
        return "newComment";
    }

    @GetMapping("/editComment/{commentId}")
    public String editComment(@PathVariable(value = "commentId") Integer id, Model model, HttpSession session) {
        model.addAttribute("username", session.getAttribute("username"));
        String username = session.getAttribute("username").toString();
        Integer userId = userService.getUserByUsername(username).getId();
        model.addAttribute("userId", userId);
        model.addAttribute("comment", taskService.getCommentById(id));
        return "editComment";
    }

    @GetMapping("/task-history/{taskId}")
    public String taskHistory(@PathVariable(value = "taskId") Integer id, Model model, HttpSession session) throws SQLException, JsonProcessingException {
        model.addAttribute("username", session.getAttribute("username"));
        List<TaskHistory> taskHistoryList = taskService.getTaskHistoryList(id);
        List<TaskHistoryData> previousDataList = new ArrayList<>();
        List<TaskHistoryData> updatedDataList = new ArrayList<>();
        List<Timestamp> createdList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();

        for (int i = 0; i < taskHistoryList.size(); i++) {
            TaskHistoryData previousData = mapper.readValue(taskHistoryList.get(i).getPreviousData(), TaskHistoryData.class);
            TaskHistoryData updatedData = mapper.readValue(taskHistoryList.get(i).getUpdatedData(), TaskHistoryData.class);
            previousDataList.add(previousData);
            updatedDataList.add(updatedData);
            createdList.add(taskHistoryList.get(i).getCreated());
        }
        model.addAttribute("previousData", previousDataList);
        model.addAttribute("updatedData", updatedDataList);
        model.addAttribute("createdList", createdList);
        return "task-history";
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
    public String submitNewTask(@ModelAttribute Task task, Model model, HttpSession session) {
        model.addAttribute("taskform", task);
        if (taskService.validateTaskData(task) == true) {
            session.getAttribute("username");
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            CustomUserDetails customUser = (CustomUserDetails) authentication.getPrincipal();
            Integer userId = customUser.getUser().getId();
            task.setReporter(userId);
            taskService.storeTask(task);
            return "redirect:/success";
        }
        return "redirect:/invalid";
    }

    @PostMapping("/edittask/{taskId}")
    public String updateTask(@ModelAttribute Task task) throws SQLException, JsonProcessingException {

        if (!taskService.validateTaskData(task)) {
            return "redirect:/invalid";
        }

        taskService.updateTaskHistory(task);
        taskService.updateTask(task);
        return "redirect:/success";
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

    @PostMapping("/newComment")
    public String addComment(int taskId, String comment, int userId) throws SQLException {
        taskService.addNewComment(taskId, comment, userId);
        return "redirect:/task/" + taskId;

    }

    @PostMapping("/editComment")
    public String editComment(int commentId, String comment, HttpSession session) throws SQLException {
        session.getAttribute("username");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUser = (CustomUserDetails) authentication.getPrincipal();
        Integer userId = customUser.getUser().getId();
        taskService.editComment(commentId, comment, userId);
        int taskId = taskService.getCommentById(commentId).getTaskId();
        return "redirect:/task/" + taskId;
    }
}
