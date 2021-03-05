package com.app.services;

import com.app.dao.KanbanBoardDao;
import com.app.dao.TaskDao;
import com.app.model.Status;
import com.app.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class KanbanBoardService {

    @Autowired
    private KanbanBoardDao kanbanBoardDao;

    @Autowired
    private TaskDao taskDao;

    public List<Status> getTaskStatuses() {
        return taskDao.getTaskStatuses();
    }

    public HashMap<String, List<Task>> getTasksByStatus() {
        HashMap<String, List<Task>> tasksByStatus = new HashMap<String, List<Task>>();
        List<Status> statusList = taskDao.getTaskStatuses();
        for (Status status : statusList) {
            tasksByStatus.put(status.getStatus(), kanbanBoardDao.getTasksByStatus(status.getStatus()));
        }
        return tasksByStatus;
    }
}
