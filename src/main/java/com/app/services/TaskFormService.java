package com.app.services;

import com.app.model.TaskForm;
import org.springframework.stereotype.Service;

@Service
public class TaskFormService {

    public boolean validateTaskData(TaskForm taskForm) {

        boolean isValid = true;

        while (isValid = true) {
            if (taskForm.getProject().isEmpty()) {
                isValid = false;
            }

            if (taskForm.getSummary().isEmpty()) {
                isValid = false;
            }

            if (taskForm.getSummary().length() > 100) {
                isValid = false;
            }

            if (taskForm.getDescription().length() > 4000) {
                isValid = false;
            }

            break;
        }
        return isValid;
    }
}
