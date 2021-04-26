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

    public boolean deleteSprint(Integer id) {
        if (!sprintDao.isTaskInSprint(id)) {
            sprintDao.deleteSprint(id);
        }
        return false;
    }

    public List<Task> getTasksInSprint(Integer sprintId) {
        return sprintDao.getTasksInSprint(sprintId);
    }
}
