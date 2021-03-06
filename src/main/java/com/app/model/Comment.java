package com.app.model;

import java.sql.Date;

public class Comment {

    private int id;
    private int taskId;
    private String comment;
    private int userId;
    private int attachmentId;
    private Date created;
    private Date modified;
    private String modified_by;

    public Comment(int taskId, String comment, int userId) {
        this.taskId = taskId;
        this.comment = comment;
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


    public int getUserId() {
        return userId;
    }

    public int getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(int attachmentId) {
        this.attachmentId = attachmentId;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public String getModified_by() {
        return modified_by;
    }

    public void setModified_by(String modified_by) {
        this.modified_by = modified_by;
    }
}
