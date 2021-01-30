package com.app.services;

import com.app.dao.TaskListDao;
import com.app.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskListService {

    @Autowired
    private TaskListDao taskListDao;

    public List<Task> getTaskList() {
        return taskListDao.getTaskList();
    }
}
