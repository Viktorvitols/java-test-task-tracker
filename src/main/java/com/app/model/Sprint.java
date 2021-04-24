package com.app.model;

import java.sql.Date;
import java.util.List;

public class Sprint {

    private int id;
    private String name;
    private Date startDate;
    private Date endDate;
    private String description;
    private List<Integer> tasklist;
    private String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Integer> getTasklist() {
        return tasklist;
    }

    public void setTasklist(List<Integer> tasklist) {
        this.tasklist = tasklist;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
