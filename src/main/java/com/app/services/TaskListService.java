package com.app.services;

import com.app.dao.TaskDao;
import com.app.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class TaskListService {

    @Autowired
    private TaskDao taskDao;

    public List<Task> getTaskList() {
        return taskDao.getTaskList();
    }
    public List<Task> getTaskById(Integer id) throws SQLException {
        return taskDao.getTaskById(id);
    }
}