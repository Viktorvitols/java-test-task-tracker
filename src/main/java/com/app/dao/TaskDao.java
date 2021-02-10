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
public class TaskDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public void storeTask(Task task) throws NullPointerException {
        jdbcTemplate.update("INSERT INTO tickets (project_name, summary, assignee_name, description) VALUES (?, ?, ?, ?)",
                task.getProject(), task.getSummary(), task.getAssignee(), task.getDescription());
    }

    public void updateTask(Task task) throws NullPointerException {
        jdbcTemplate.update("UPDATE tickets SET project_name = ?, summary = ?, assignee_name = ?, description = ? WHERE id = ?",
                task.getProject(), task.getSummary(), task.getAssignee(), task.getDescription(), task.getId());
    }

    public void changeAssignee(int taskId, int userId) throws NullPointerException {
        jdbcTemplate.update("UPDATE tickets " +
                        "SET assignee = (" +
                        "SELECT id FROM users WHERE id = ? AND is_active = true), " +
                        "assignee_name = (" +
                        "SELECT name FROM users WHERE id = ? AND is_active = true) WHERE id = ?",
                userId, userId, taskId);
    }

    public Task getTaskById(Integer id) throws SQLException {
        RowMapper<Task> rowMapper = (resultSet, rowNumber) -> mapTask(resultSet);
        return jdbcTemplate.query("SELECT * FROM tickets WHERE id = ?", rowMapper, id).get(0);
    }

    public List<Task> getTaskList() {
        RowMapper<Task> rowMapper = (resultSet, rowNumber) -> mapTask(resultSet);
        return jdbcTemplate.query("SELECT id, project_name, summary, assignee_name, description FROM tickets ORDER BY id", rowMapper);
    }

    public List<Task> getDetailedTaskList() {
        RowMapper<Task> rowMapper = (resultSet, rowNumber) -> mapTask(resultSet);
        return jdbcTemplate.query("SELECT * FROM tickets ORDER BY id", rowMapper);
    }

    public List<Task> getFilteredTaskList(String project) {
        RowMapper<Task> rowMapper = (resultSet, rowNumber) -> mapTask(resultSet);
        return jdbcTemplate.query("SELECT * FROM tickets WHERE project_name = ? ORDER BY id", rowMapper, project);
    }

    public List<Task> searchTaskBySummary(String varSummary) {
        RowMapper<Task> rowMapper = (resultSet, rowNumber) -> mapTask(resultSet);
        String likeSummary = "%" + varSummary.substring(2).toUpperCase() + "%";
        return jdbcTemplate.query("SELECT * FROM tickets WHERE UPPER(summary) LIKE ? OR UPPER(description) LIKE ?", rowMapper, likeSummary, likeSummary);
    }


    private Task mapTask(ResultSet resultSet) throws SQLException {
        Task task = new Task();

        task.setId(resultSet.getInt("id"));
        task.setProject(resultSet.getString("project_name"));
        task.setSummary(resultSet.getString("summary"));
//        task.setCreated(resultSet.getDate("created"));
//        task.setReporter(resultSet.getString("reporter"));
        task.setAssigneeName(resultSet.getString("assignee_name"));
        task.setDescription(resultSet.getString("description"));
//        task.setAttachmentId(resultSet.getInt("attachment_id"));

        return task;
    }
}
