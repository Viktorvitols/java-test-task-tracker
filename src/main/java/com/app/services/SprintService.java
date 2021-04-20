package com.app.services;

import com.app.dao.SprintDao;
import com.app.dao.TaskDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SprintService {

    @Autowired
    private TaskDao taskDao;

    @Autowired
    private SprintDao sprintDao;


}
