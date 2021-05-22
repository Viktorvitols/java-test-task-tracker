package com.app.services;

import com.app.dao.RoadMapDao;
import com.app.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class RoadMapService {
    @Autowired
    private RoadMapDao roadMapDao;

    public HashMap<Integer, List<Task>> getTaskByMonth(Integer year) {
        HashMap<Integer, List<Task>> tasksByMonth = new HashMap<Integer, List<Task>>();
        for (int monthId = 0; monthId < 12; monthId++) {
            List<Task> tasks = roadMapDao.getTaskByMonth(monthId, year);
            tasksByMonth.put(monthId, tasks);
        }
        return tasksByMonth;
    }

    public List<Task> getTaskNoData() {
        return roadMapDao.getTaskNoData();
    }
}
