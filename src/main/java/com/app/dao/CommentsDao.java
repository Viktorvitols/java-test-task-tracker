package com.app.dao;

import com.app.model.Comment;
import com.app.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CommentsDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Comment> getTaskCommentList(int id) {
        RowMapper<Comment> rowMapper = (resultSet, rowNumber) -> mapGetComment(resultSet);
        return jdbcTemplate.query("SELECT comments.ticket_id, comments.id, comments.text, " +
                "comments.user_id AS user_id, comments.created, " +
                "comments.is_modified, users.name AS user, users.email, " +
                "(SELECT name FROM users WHERE users.id = comments.modified_by) AS mod_by " +
//                ???????????????????????????????
                "FROM comments " +
                "INNER JOIN users ON comments.user_id = users.id " +
                "WHERE ticket_id =?", rowMapper, id);
    }

    public void addNewComment (String comment) {
        jdbcTemplate.update("INSERT INTO comments (ticket_id, text, user_id, ) VALUES ");
    }

    private Comment mapGetComment(ResultSet resultSet) throws SQLException {
        Comment comment = new Comment(resultSet.getInt("ticket_id"));
        comment.setId(resultSet.getInt("id"));
        comment.setComment(resultSet.getString("text"));
        comment.setUser(resultSet.getString("user"));
        comment.setCreated(resultSet.getDate("created"));
        comment.setModified(resultSet.getBoolean("is_modified"));
        comment.setModified_by(resultSet.getString("mod_by"));
        return comment;
    }

//    private User mapUser(ResultSet resultSet) throws SQLException {
//        User user = new User();
//        user.setId(resultSet.getInt("id"));
//        user.setName(resultSet.getString("name"));
//        user.setEmail(resultSet.getString("email"));
//        return user;
//    }
}
