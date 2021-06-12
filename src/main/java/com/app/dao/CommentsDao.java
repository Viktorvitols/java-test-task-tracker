package com.app.dao;

import com.app.model.Comment;
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
                "comments.is_modified, comments.last_modified, users.name AS user, users.email, " +
                "(SELECT name FROM users WHERE users.id = comments.modified_by) AS mod_by " +
                "FROM comments " +
                "INNER JOIN users ON comments.user_id = users.id " +
                "WHERE ticket_id =? " +
                "ORDER BY created", rowMapper, id);
    }

    public void addNewComment(int taskId, String comment, int userId) {
        jdbcTemplate.update("INSERT INTO comments (ticket_id, text, user_id) VALUES (?, ?, ?)", taskId, comment, userId);
        jdbcTemplate.update("UPDATE tickets SET modified_at = current_timestamp WHERE id = ?", taskId);
    }

    public List<Comment> getCommentListById(int commentId) {
        RowMapper<Comment> rowMapper = (resultSet, rowNumber) -> mapGetCommentById(resultSet);
        return jdbcTemplate.query("SELECT * FROM comments WHERE id=?", rowMapper, commentId);
    }

    public void editComment(int commentId, String comment, int userId) {
        jdbcTemplate.update("UPDATE comments SET text=?, modified_by=?, is_modified=true, " +
                "last_modified=CURRENT_TIMESTAMP WHERE id=?", comment, userId, commentId);
    }


    public List<Integer> getCommentCount(int taskId) {
        RowMapper<Integer> rowMapper = (resultSet, rowNumber) -> mapGetCommentCount(resultSet);
        return jdbcTemplate.query("SELECT COUNT(*) FROM comments WHERE ticket_id = ?", rowMapper, taskId);
    }

    private Comment mapGetCommentById(ResultSet resultSet) throws SQLException {
        Comment comment = new Comment();
        comment.setId(resultSet.getInt("id"));
        comment.setId(resultSet.getInt("id"));
        comment.setTaskId(resultSet.getInt("ticket_id"));
        comment.setComment(resultSet.getString("text"));
        comment.setAttachmentId(resultSet.getInt("attachment_id"));
        comment.setCreatedTS(resultSet.getTimestamp("created"));
        comment.setModified_by(resultSet.getInt("modified_by"));
        comment.setModified(resultSet.getBoolean("is_modified"));
        comment.setLastModifiedTS(resultSet.getTimestamp("last_modified"));
        return comment;
    }

    private Comment mapGetComment(ResultSet resultSet) throws SQLException {
        Comment comment = new Comment();
        comment.setTaskId(resultSet.getInt("ticket_id"));
        comment.setUserId(resultSet.getInt("user_id"));
        comment.setId(resultSet.getInt("id"));
        comment.setComment(resultSet.getString("text"));
        comment.setUsername(resultSet.getString("user"));
        comment.setCreatedTS(resultSet.getTimestamp("created"));
        comment.setModified(resultSet.getBoolean("is_modified"));
        comment.setMod_by(resultSet.getString("mod_by"));
        comment.setLastModifiedTS(resultSet.getTimestamp("last_modified"));
        return comment;
    }

    private Integer mapGetCommentCount(ResultSet resultSet) throws SQLException {
        Integer count = resultSet.getInt("count");
        return count;
    }
}
