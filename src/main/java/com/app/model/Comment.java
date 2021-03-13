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
    private int modified_by;
    private String mod_by;
    private Timestamp lastModifiedTS;
    private String lastModified;

    public Comment(int taskId, String comment, int userId) {
        this.taskId = taskId;
        this.comment = comment;
        this.userId = userId;
    }

    public Comment(int taskId, int userId) {
        this.taskId = taskId;
        this.userId = userId;
    }

    public Comment(int id) {
        this.id = id;
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

    public void setTaskId(int taskId) {
        this.taskId = taskId;
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

    public Integer getModified_by() {
        return modified_by;
    }

    public void setModified_by(Integer modified_by) {
        this.modified_by = modified_by;
    }

    public String getMod_by() {
        return mod_by;
    }

    public void setMod_by(String mod_by) {
        this.mod_by = mod_by;
    }

    public void setLastModifiedTS(Timestamp lastModifiedTS) {
        this.lastModifiedTS = lastModifiedTS;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.lastModified = formatter.format(lastModifiedTS);
    }

    public String getLastModified() {
        return lastModified;
    }

    public Timestamp getLastModifiedTS() {
        return lastModifiedTS;
    }
}
