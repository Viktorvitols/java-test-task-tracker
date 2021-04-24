package com.app.dao;

import com.app.model.Sprint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@Repository
public class SprintDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Sprint> getSprintList() {
        RowMapper<Sprint> rowMapper = (resultSet, rowNumber) -> mapSprint(resultSet);
        return jdbcTemplate.query("SELECT * FROM sprints ORDER BY start_date", rowMapper);
    }

    public Sprint getSprintById(int id) {
        RowMapper<Sprint> rowMapper = (resultSet, rowNumber) -> mapSprint(resultSet);
        return jdbcTemplate.query("SELECT * FROM sprints WHERE id = ?", rowMapper, id).get(0);
    }

    private Sprint mapSprint(ResultSet resultSet) throws SQLException {
        Sprint sprint = new Sprint();

        sprint.setId(resultSet.getInt("id"));
        sprint.setName(resultSet.getString("name"));
        sprint.setStartDate(resultSet.getDate("start_date"));
        sprint.setEndDate(resultSet.getDate("end_date"));
        sprint.setDescription(resultSet.getString("description"));
        sprint.setTasklist(Arrays.asList((Integer[]) resultSet.getArray("ticket_list").getArray()));
        sprint.setStatus(resultSet.getString("status"));

        return sprint;
    }
}
