package com.app.dao;

import com.app.model.Status;
import com.app.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.SplittableRandom;

@Repository
public class TaskDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public void storeTask(Task task) throws NullPointerException {
        jdbcTemplate.update("INSERT INTO tickets (project_name, status, summary, assignee_name, description) VALUES (?, ?, ?, ?, ?)",
                task.getProject(), task.getStatus(), task.getSummary(), task.getAssignee(), task.getDescription());
    }

    public void updateTask(Task task) throws NullPointerException {
        jdbcTemplate.
                update("UPDATE tickets SET project_name = ?, status = ?::status, summary = ?, assignee_name = ?, description = ? WHERE id = ?",
                        task.getProject(), task.getStatus(), task.getSummary(), task.getAssignee(), task.getDescription(), task.getId());
    }

    public void changeAssignee(int taskId, int userId) throws NullPointerException {
        jdbcTemplate.update("UPDATE tickets " +
                        "SET assignee = (" +
                        "SELECT id FROM users WHERE id = ? AND is_active = true), " +
                        "assignee_name = (" +
                        "SELECT name FROM users WHERE id = ? AND is_active = true) WHERE id = ?",
                userId, userId, taskId);
    }

    public List<Status> getTaskStatuses() {
        RowMapper<Status> rowMapper = (resultSet, rowNumber) -> mapStatus(resultSet);
        return jdbcTemplate.query("SELECT unnest(enum_range(NULL::status))::text AS status", rowMapper);
//        return jdbcTemplate.query("SELECT unnest(enum_range(NULL::status))");

//        SELECT unnest(enum_range(NULL::status))
    }

    public void changeStatus(int taskId, String status) throws NullPointerException {
        jdbcTemplate.update("UPDATE tickets SET status = ? WHERE id = ?", status, taskId);
    }

    public void addNewAfterStatus(String newStatus, String afterStatus) {
        final String ADD_TO_STATUS = "ALTER TYPE status ADD VALUE '" + newStatus + "' AFTER '" + afterStatus + "'";
        jdbcTemplate.execute(ADD_TO_STATUS);
    }

    public void addNewStatus(String newStatus) {
        final String ADD_TO_STATUS = "ALTER TYPE status ADD VALUE '" + newStatus + "'";
        jdbcTemplate.execute(ADD_TO_STATUS);
    }

    public void removeStatus(String oldStatus) {
        final String DROP_FROM_STATUS = "DELETE FROM pg_enum WHERE enumlabel = '" + oldStatus + "' AND enumtypid =" +
                "(SELECT oid FROM pg_type WHERE typname = 'status')";
        jdbcTemplate.execute(DROP_FROM_STATUS);
    }


    public Task getTaskById(Integer id) throws SQLException {
        RowMapper<Task> rowMapper = (resultSet, rowNumber) -> mapTask(resultSet);
        return jdbcTemplate.query("SELECT * FROM tickets WHERE id = ?", rowMapper, id).get(0);
    }

    public List<Task> getTaskList() {
        RowMapper<Task> rowMapper = (resultSet, rowNumber) -> mapTask(resultSet);
        return jdbcTemplate.query("SELECT id, project_name, status, summary, assignee_name, description FROM tickets ORDER BY id", rowMapper);
    }

    public List<Task> getDetailedTaskList() {
        RowMapper<Task> rowMapper = (resultSet, rowNumber) -> mapTask(resultSet);
        return jdbcTemplate.query("SELECT * FROM tickets ORDER BY id", rowMapper);
    }

    public List<Task> getFilteredTaskList(String project) {
        RowMapper<Task> rowMapper = (resultSet, rowNumber) -> mapTask(resultSet);
        return jdbcTemplate.query("SELECT * FROM tickets WHERE project_name = ? ORDER BY id", rowMapper, project);
    }

    public List<Task> searchTaskByText(String varSummary) {
        RowMapper<Task> rowMapper = (resultSet, rowNumber) -> mapTask(resultSet);
        String likeSummary = "%" + varSummary.substring(2).toUpperCase() + "%";
        return jdbcTemplate.query("SELECT * FROM tickets WHERE UPPER(summary) LIKE ? OR UPPER(description) LIKE ?", rowMapper, likeSummary, likeSummary);
    }


    private Task mapTask(ResultSet resultSet) throws SQLException {
        Task task = new Task();

        task.setId(resultSet.getInt("id"));
        task.setProject(resultSet.getString("project_name"));
        task.setStatus(resultSet.getString("status"));
        task.setSummary(resultSet.getString("summary"));
//        task.setCreated(resultSet.getDate("created"));
//        task.setReporter(resultSet.getString("reporter"));
        task.setAssigneeName(resultSet.getString("assignee_name"));
        task.setDescription(resultSet.getString("description"));
//        task.setAttachmentId(resultSet.getInt("attachment_id"));
        return task;
    }

    private Status mapStatus(ResultSet resultSet) throws SQLException {
        Status status = new Status();
        status.setStatus(resultSet.getString("status"));
        return status;
    }
}
