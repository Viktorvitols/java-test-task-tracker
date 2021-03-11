package com.app.model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Comment {

    private int id;
    private int taskId;
    private String comment;
    private String username;
    private int userId;
    private int attachmentId;
    private Timestamp createdTS;
    private String created;
    private boolean modified;
    private String modified_by;

    public Comment(int taskId, String comment, int userId) {
        this.taskId = taskId;
        this.comment = comment;
        this.userId = userId;
    }

    public Comment(int taskId, int userId) {
        this.taskId = taskId;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTaskId() {
        return taskId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(int attachmentId) {
        this.attachmentId = attachmentId;
    }

    public Timestamp getCreatedTS() {
        return createdTS;
    }

    public void setCreatedTS(Timestamp createdTS) {
        this.createdTS = createdTS;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.created = formatter.format(createdTS);
    }

    public String getCreated() {
        return created;
    }

    public boolean isModified() {
        return modified;
    }

    public void setModified(boolean modified) {
        this.modified = modified;
    }

    public String getModified_by() {
        return modified_by;
    }

    public void setModified_by(String modified_by) {
        this.modified_by = modified_by;
    }
}
