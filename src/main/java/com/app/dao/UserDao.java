package com.app.dao;

import com.app.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean isUserActive(int userId) {
        RowMapper<User> rowMapper = (resultSet, rowNumber) -> mapUser(resultSet);
        if (jdbcTemplate.query("SELECT * FROM users WHERE id = ?", rowMapper, userId).isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public List<User> getUserList() {
        RowMapper<User> rowMapper = (resultSet, rowNumber) -> mapUser(resultSet);
        return jdbcTemplate.query("SELECT * FROM users ORDER BY name", rowMapper);
    }

    private User mapUser(ResultSet resultSet) throws SQLException {
        User user = new User();

        user.setId(resultSet.getInt("id"));
        user.setName(resultSet.getString("name"));
        user.setEmail(resultSet.getString("email"));
        user.setActive(resultSet.getBoolean("is_active"));

        return user;
    }
}
