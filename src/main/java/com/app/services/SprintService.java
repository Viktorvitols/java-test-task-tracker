package com.app.services;

import com.app.dao.SprintDao;
import com.app.dao.TaskDao;
import com.app.model.Sprint;
import com.app.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SprintService {

    @Autowired
    private TaskDao taskDao;

    @Autowired
    private SprintDao sprintDao;

    public List<Sprint> getSprintList() {
        return sprintDao.getSprintList();
    }

    public Sprint getSprintById(int id) {
        return sprintDao.getSprintById(id);
    }

    public void createNewSprint(Sprint sprint) {
        sprintDao.createNewSprint(sprint);
    }

    public void addTaskToSprint(Integer sprintId, Integer taskId) {
        sprintDao.addTaskToSprint(sprintId, taskId);
    }

    public boolean deleteSprint(Integer id) {
        if (!sprintDao.isTaskInSprint(id)) {
            sprintDao.deleteSprint(id);
            return true;
        } else {
            return false;
        }
    }

    public List<Task> getTasksInSprint(Integer sprintId) {
        return sprintDao.getTasksInSprint(sprintId);
    }

    public List<Task> getTasksByStartDate(Sprint sprint) {
        return sprintDao.getTasksByStartDate(sprint);
    }

    public Boolean isLastTaskClosed(Integer sprintId) {
        return sprintDao.isLastTaskClosed(sprintId);
    }

    public void closeSprint(Integer sprintId) {
        sprintDao.closeSprint(sprintId);
    }
}