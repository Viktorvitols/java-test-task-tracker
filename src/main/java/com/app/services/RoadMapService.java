package com.app.services;

import com.app.dao.RoadMapDao;
import com.app.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Service
public class RoadMapService {
    @Autowired
    private RoadMapDao roadMapDao;

    public HashMap<String, List<Task>> getTaskByMonth(Integer year, List<String> months) {
        HashMap<String, List<Task>> tasksByMonth= new HashMap<String, List<Task>>();
        Integer monthId = LocalDateTime.now().getMonthValue();
        for (String month : months) {
            switch (month) {
                case "January":
                    monthId = 1;
                    break;
                case "February":
                    monthId = 2;
                    break;
                case "March":
                    monthId = 3;
                    break;
                case "April":
                    monthId = 4;
                    break;
                case "May":
                    monthId = 5;
                    break;
                case "June":
                    monthId = 6;
                    break;
                case "July":
                    monthId = 7;
                    break;
                case "August":
                    monthId = 8;
                    break;
                case "September":
                    monthId = 9;
                    break;
                case "October":
                    monthId = 10;
                    break;
                case "November":
                    monthId = 11;
                    break;
                case "December":
                    monthId = 12;
                    break;

            }
            List<Task> tasks = roadMapDao.getTaskByMonth(monthId, year);
            tasksByMonth.put(month, tasks);
        }
        return tasksByMonth;
    }
}
