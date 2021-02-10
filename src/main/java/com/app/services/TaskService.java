package com.app.services;

import com.app.controllers.TaskController;
import com.app.dao.TaskDao;
import com.app.dao.UserDao;
import com.app.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskDao taskDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private TaskController taskController;

    public boolean validateTaskData(Task task) {

        boolean isValid = true;

        while (isValid = true) {
            if (task.getProject().isEmpty()) {
                isValid = false;
            }

            if (task.getSummary().isEmpty()) {
                isValid = false;
            }

            if (task.getSummary().length() > 100) {
                isValid = false;
            }

            if (task.getDescription().length() > 4000) {
                isValid = false;
            }

            break;
        }
        return isValid;
    }

    public void storeTask(Task task) {
        taskDao.storeTask(task);
    }

    public void updateTask(Task task) throws SQLException {
        taskDao.updateTask(task);
    }

    public Task getTaskById(Integer id) throws SQLException {
        return taskDao.getTaskById(id);
    }

    public void changeAssignee(Integer taskId, Integer userId) {
        if (userDao.isUserActive(userId) == true) {
            taskDao.changeAssignee(taskId, userId);
        } else {
            taskController.getInvalidForm();
        }
    }

    public List<Task> searchTask(String searchString) {
        return taskDao.searchTaskBySummary(searchString);
    }
}