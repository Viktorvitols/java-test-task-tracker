package com.app.dao;

import com.app.model.Sprint;
import com.app.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class SprintDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Sprint> getSprintList() {
        RowMapper<Sprint> rowMapper = (resultSet, rowNumber) -> mapSprint(resultSet);
        return jdbcTemplate.query("SELECT * FROM sprints ORDER BY start_date", rowMapper);
    }

    public Sprint getSprintById(int id) {
        RowMapper<Sprint> rowMapper = (resultSet, rowNumber) -> mapSprint(resultSet);
        return jdbcTemplate.query("SELECT * FROM sprints WHERE id = ?", rowMapper, id).get(0);
    }

    public List<Task> getTasksInSprint(Integer sprintId) {
        RowMapper<Task> rowMapper = (resultSet, rowNumber) -> mapSprintTasks(resultSet);
        return jdbcTemplate.query("SELECT * FROM tickets WHERE sprint_id = ?", rowMapper, sprintId);
    }

    public List<Task> getTasksByStartDate(Sprint sprint) {
        RowMapper<Task> rowMapper = (resultSet, rowNumber) -> mapSprintTasks(resultSet);
        return jdbcTemplate.query("SELECT * FROM tickets WHERE start_date >= ? AND due_date <= ?",
                rowMapper, sprint.getStartDate(), sprint.getEndDate());
    }


    private Sprint mapSprint(ResultSet resultSet) throws SQLException {
        Sprint sprint = new Sprint();

        sprint.setId(resultSet.getInt("id"));
        sprint.setName(resultSet.getString("name"));
        sprint.setStartDate(resultSet.getDate("start_date"));
        sprint.setEndDate(resultSet.getDate("end_date"));
        sprint.setDescription(resultSet.getString("description"));
        sprint.setStatus(resultSet.getString("status"));

        return sprint;
    }

    public void createNewSprint(Sprint sprint) {
        jdbcTemplate.update("INSERT INTO sprints (name, start_date, end_date, description) " +
                        "VALUES (?, ?, ?, ?)",
                sprint.getName(), sprint.getStartDate(), sprint.getEndDate(), sprint.getDescription());
    }

    public void addTaskToSprint(Integer sprintId, Integer taskId) {
        jdbcTemplate.update("UPDATE tickets SET sprint_id = ? WHERE id = ?", sprintId, taskId);
    }

    public void closeSprint(Integer sprintId) {
        jdbcTemplate.update("UPDATE sprints SET status = 'DONE' WHERE id = ?", sprintId);
    }

    public void deleteSprint(Integer sprint_id) {
        jdbcTemplate.update("DELETE FROM sprints WHERE id = ?", sprint_id);
    }

    public Boolean isTaskInSprint(Integer sprint_id) {
        RowMapper<Task> rowMapper = (resultSet, rowNumber) -> mapSprintTasks(resultSet);
        return !jdbcTemplate.query("SELECT * FROM tickets WHERE sprint_id = ?",
                rowMapper, sprint_id).isEmpty();
    }

    public Boolean isLastTaskClosed(Integer sprintId) {
        RowMapper<Task> rowMapper = (resultSet, rowNumber) -> mapSprintTasks(resultSet);
        return jdbcTemplate.query("SELECT * FROM tickets WHERE sprint_id = ? AND (status != 'CLOSED' AND status != 'DECLINED')",
                rowMapper, sprintId).isEmpty();
    }

    private Task mapSprintTasks(ResultSet resultSet) throws SQLException {
        Task task = new Task();

        task.setId(resultSet.getInt("id"));
        task.setStatus(resultSet.getString("status"));
        task.setSummary(resultSet.getString("summary"));
        task.setCreated(resultSet.getDate("created"));
        task.setStartDate(resultSet.getDate("start_date"));
        task.setDueDate(resultSet.getDate("due_date"));
        task.setAssignee(resultSet.getInt("assignee"));
        task.setSprintId(resultSet.getInt("sprint_id"));
        return task;
    }
}
