package com.app.dao;

import com.app.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TaskListDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Task> getTaskList() {
        RowMapper<Task> rowMapper = (resultSet, rowNumber) -> mapTask(resultSet);
        return jdbcTemplate.query("SELECT project_name, summary, assignee_name, description FROM tickets", rowMapper);
    }

    private Task mapTask(ResultSet resultSet) throws SQLException {
        Task task = new Task();

        task.setProject(resultSet.getString("project_name"));
        task.setSummary(resultSet.getString("summary"));
        task.setAssigneeName(resultSet.getString("assignee_name"));
        task.setDescription(resultSet.getString("description"));

        return task;
    }
}
