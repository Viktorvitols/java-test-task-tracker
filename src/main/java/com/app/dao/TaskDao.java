package com.app.dao;

import com.app.model.Status;
import com.app.model.Task;
import com.app.model.TaskChangeableData;
import com.app.model.TaskHistory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
        jdbcTemplate.update("INSERT INTO tickets (project_name, summary, reporter, assignee, description) VALUES (?, ?, ?, ?, ?)",
                task.getProject(), task.getSummary(), task.getReporter(), task.getAssignee(), task.getDescription());
    }

    public void updateTask(Task task) throws NullPointerException {
        jdbcTemplate.
                update("UPDATE tickets " +
                                "SET project_name = ?, status = ?::status, summary = ?, assignee = ?, description = ?, start_date = ?, due_date = ?" +
                                "WHERE id = ?",
                        task.getProject(), task.getStatus(), task.getSummary(), task.getAssignee(), task.getDescription(), task.getStartDate(), task.getDueDate(), task.getId());
    }

    private TaskHistory mapTaskHistory(ResultSet resultSet) throws SQLException {
        TaskHistory taskHistory = new TaskHistory();
        taskHistory.setId(resultSet.getInt("id"));
        taskHistory.setTaskId(resultSet.getInt("ticket_id"));
        taskHistory.setPreviousData(resultSet.getString("previous_data"));
        taskHistory.setUpdatedData(resultSet.getString("updated_data"));
        taskHistory.setCreated(resultSet.getTimestamp("created"));
        return taskHistory;
    }

    public List<TaskHistory> getTaskHistory(int taskId) {
        RowMapper<TaskHistory> rowMapper = (resultSet, rowNumber) -> mapTaskHistory(resultSet);
        return jdbcTemplate.query("SELECT * FROM ticket_history WHERE ticket_id = ? ORDER BY created DESC", rowMapper, taskId);
    }

    private TaskChangeableData setHistoryPreviousData(Task task) {
        TaskChangeableData previousData = new TaskChangeableData();
        previousData.setTaskId(task.getId());
        previousData.setProject(task.getProject());
        previousData.setAssignee(task.getAssignee());
        previousData.setSummary(task.getSummary());
        previousData.setDescription(task.getDescription());
        previousData.setStartDate(task.getStartDate());
        previousData.setDueDate(task.getDueDate());
        return previousData;
    }

    private TaskChangeableData setHistoryUpdatedData(Task task) {
        TaskChangeableData updatedData = new TaskChangeableData();
        updatedData.setTaskId(task.getId());
        updatedData.setProject(task.getProject());
        updatedData.setAssignee(task.getAssignee());
        updatedData.setSummary(task.getSummary());
        updatedData.setDescription(task.getDescription());
        updatedData.setStartDate(task.getStartDate());
        updatedData.setDueDate(task.getDueDate());
        return updatedData;
    }

    public void updateTaskHistory(Task task) throws SQLException, JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        String jsonOld = mapper.writeValueAsString(setHistoryPreviousData(getTaskById(task.getId())));
        String jsonNew = mapper.writeValueAsString(setHistoryUpdatedData(task));

        jdbcTemplate.update("INSERT INTO ticket_history (ticket_id, previous_data, updated_data) " +
                "VALUES (?, ?::json, ?::json)", task.getId(), jsonOld, jsonNew);
    }

    public void changeAssignee(int taskId, int userId) throws NullPointerException {
        jdbcTemplate.update("UPDATE tickets " +
                        "SET assignee = (" +
                        "SELECT id FROM users WHERE id = ? AND is_active = true), " +
                        "WHERE id = ?",
                userId, userId, taskId);
    }


    private Status mapStatus(ResultSet resultSet) throws SQLException {
        Status status = new Status();
        status.setStatus(resultSet.getString("status"));
        return status;
    }

    public List<Status> getTaskStatuses() {
        RowMapper<Status> rowMapper = (resultSet, rowNumber) -> mapStatus(resultSet);
        return jdbcTemplate.query("SELECT unnest(enum_range(NULL::status))::text AS status", rowMapper);
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


    private Task mapTask(ResultSet resultSet) throws SQLException {
        Task task = new Task();

        task.setId(resultSet.getInt("id"));
        task.setProject(resultSet.getString("project_name"));
        task.setStatus(resultSet.getString("status"));
        task.setSummary(resultSet.getString("summary"));
        task.setCreated(resultSet.getDate("created"));
        task.setStartDate(resultSet.getDate("start_date"));
        task.setDueDate(resultSet.getDate("due_date"));
        task.setReporter(resultSet.getInt("reporter"));
        task.setAssignee(resultSet.getInt("assignee"));
        task.setDescription(resultSet.getString("description"));
        task.setAttachmentId(resultSet.getArray("attachment_id"));
        task.setSprintId(resultSet.getInt("sprint_id"));
        return task;
    }

    public Task getTaskById(Integer id) throws SQLException {
        RowMapper<Task> rowMapper = (resultSet, rowNumber) -> mapTask(resultSet);
        return jdbcTemplate.query("SELECT * FROM tickets WHERE id = ?", rowMapper, id).get(0);
    }

    public List<Task> getTaskList() {
        RowMapper<Task> rowMapper = (resultSet, rowNumber) -> mapTask(resultSet);
        List<Task> taskList = jdbcTemplate.query("SELECT * FROM tickets ORDER BY id", rowMapper);
        RowMapper<Integer> rowMapper1 = (resultSet, rowNumber) -> mapGetCommentCount(resultSet);
        for (int i = 0; i < taskList.size(); i++) {
            Integer taskId = taskList.get(i).getId();
            Integer commentCount = jdbcTemplate.query("SELECT COUNT(*) FROM comments WHERE ticket_id = ?", rowMapper1, taskId).get(0);
            taskList.get(i).setCommentCount(commentCount);
        }
        return taskList;
    }

    public List<Task> getDetailedTaskList() {
        RowMapper<Task> rowMapper = (resultSet, rowNumber) -> mapTask(resultSet);
        List<Task> taskList = jdbcTemplate.query("SELECT * FROM tickets ORDER BY id", rowMapper);
        RowMapper<Integer> rowMapper1 = (resultSet, rowNumber) -> mapGetCommentCount(resultSet);
        for (int i = 0; i < taskList.size(); i++) {
            Integer taskId = taskList.get(i).getId();
            Integer commentCount = jdbcTemplate.query("SELECT COUNT(*) FROM comments WHERE ticket_id = ?", rowMapper1, taskId).get(0);
            taskList.get(i).setCommentCount(commentCount);
        }
        return taskList;
    }

    public List<Task> getFilteredTaskList(String project) {
        RowMapper<Task> rowMapper = (resultSet, rowNumber) -> mapTask(resultSet);
        List<Task> taskList = jdbcTemplate.query("SELECT * FROM tickets WHERE project_name = ? ORDER BY id", rowMapper, project);
        RowMapper<Integer> rowMapper1 = (resultSet, rowNumber) -> mapGetCommentCount(resultSet);
        for (int i = 0; i < taskList.size(); i++) {
            Integer taskId = taskList.get(i).getId();
            Integer commentCount = jdbcTemplate.query("SELECT COUNT(*) FROM comments WHERE ticket_id = ?", rowMapper1, taskId).get(0);
            taskList.get(i).setCommentCount(commentCount);
        }
        return taskList;
    }

    private Integer mapGetCommentCount(ResultSet resultSet) throws SQLException {
        Integer count = resultSet.getInt("count");
        return count;
    }

    public List<Task> searchTaskByText(String varSummary) {
        RowMapper<Task> rowMapper = (resultSet, rowNumber) -> mapTask(resultSet);
        String likeSummary = "%" + varSummary.substring(2).toUpperCase() + "%";
        List<Task> taskList = jdbcTemplate.query("SELECT * FROM tickets WHERE UPPER(summary) LIKE ? OR UPPER(description) LIKE ?", rowMapper, likeSummary, likeSummary);
        RowMapper<Integer> rowMapper1 = (resultSet, rowNumber) -> mapGetCommentCount(resultSet);
        for (int i = 0; i < taskList.size(); i++) {
            Integer taskId = taskList.get(i).getId();
            Integer commentCount = jdbcTemplate.query("SELECT COUNT(*) FROM comments WHERE ticket_id = ?", rowMapper1, taskId).get(0);
            taskList.get(i).setCommentCount(commentCount);
        }
        return taskList;
    }

    public Integer getSprintId(Integer taskId) {
        RowMapper<Task> rowMapper = (resultSet, rowNumber) -> mapTask(resultSet);
        return jdbcTemplate.query("SELECT * FROM tickets WHERE id = ?",
                rowMapper, taskId).get(0).getSprintId();
    }
}