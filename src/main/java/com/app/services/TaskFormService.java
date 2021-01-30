package com.app.services;

import com.app.dao.TaskFormDao;
import com.app.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskFormService {

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

    @Autowired
    private TaskFormDao taskFormDao;

    public void storeTask(Task task) {
        taskFormDao.storeTask(task);
    }
}
