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
public class RoadMapDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Task> getTaskByMonth(Integer month, Integer year) {
        RowMapper<Task> rowMapper = (resultSet, rowNumber) -> mapTask(resultSet);
        return jdbcTemplate.query("SELECT * FROM tickets " +
                "WHERE EXTRACT (MONTH FROM start_date) = ?" +
                "AND EXTRACT (YEAR FROM start_date) = ? ORDER BY start_date", rowMapper, month, year);
    }

    public List<Task> getTaskNoData() {
        RowMapper<Task> rowMapper = (resultSet, rowNumber) -> mapTask(resultSet);
        return jdbcTemplate.query("SELECT * FROM tickets WHERE start_date ISNULL" +
                " ORDER BY created DESC", rowMapper);
    }

    private Task mapTask(ResultSet resultSet) throws SQLException {
        Task task = new Task();

        task.setId(resultSet.getInt("id"));
        task.setStartDate(resultSet.getDate("due_date"));
        task.setProject(resultSet.getString("project_name"));
        task.setStatus(resultSet.getString("status"));
        task.setSummary(resultSet.getString("summary"));
        return task;
    }
}
