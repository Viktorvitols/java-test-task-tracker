package com.app.dao;

import com.app.model.TaskForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TaskFormDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void storeTask(TaskForm task) throws NullPointerException {
        jdbcTemplate.update("INSERT INTO tickets (project_name, summary, assignee_name, description) VALUES (?, ?, ?, ?)",
                task.getProject(), task.getSummary(), task.getAssignee(), task.getDescription());
    }
}
