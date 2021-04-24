package com.app.services;

import com.app.dao.SprintDao;
import com.app.dao.TaskDao;
import com.app.model.Sprint;
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
}
