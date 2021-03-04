package com.app.dao;

import com.app.model.Status;
import com.app.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@Repository
public class KanbanBoardDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Task> getTasksByStatus(String status) {
        RowMapper<Task> rowMapper = (resultSet, rowNumber) -> mapTask(resultSet);
        return jdbcTemplate.query("SELECT id, project_name, summary FROM tickets WHERE status = ?::status", rowMapper, status);
    }

    private Task mapTask(ResultSet resultSet) throws SQLException {
        Task task = new Task();

        task.setId(resultSet.getInt("id"));
        task.setProject(resultSet.getString("project_name"));
//        task.setStatus(resultSet.getString("status"));
        task.setSummary(resultSet.getString("summary"));
//        task.setCreated(resultSet.getDate("created"));
//        task.setReporter(resultSet.getString("reporter"));
//        task.setAssigneeName(resultSet.getString("assignee_name"));
//        task.setDescription(resultSet.getString("description"));
//        task.setAttachmentId(resultSet.getInt("attachment_id"));
        return task;
    }

    private Status mapStatus(ResultSet resultSet) throws SQLException {
        Status status = new Status();
        status.setStatus(resultSet.getString("status"));
        return status;
    }
}
