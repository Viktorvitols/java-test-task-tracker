package com.app.controllers;

import com.app.services.RoadMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@Controller
public class RoadMapController {
    @Autowired
    private RoadMapService roadMapService;

    @GetMapping("/road-map")
    public String roadMap(HttpSession session, Model model) {
        model.addAttribute("username", session.getAttribute("username"));
        model.addAttribute("tasksByStartDate", roadMapService.getTaskByMonth(LocalDateTime.now().getYear()));
        model.addAttribute("year_to_show", LocalDateTime.now().getYear());
        model.addAttribute("tasksNoDate", roadMapService.getTaskNoData());
        return "road-map";
    }

    @GetMapping("/road-map/{year}")
    public String getRoadMap(@PathVariable Integer year,
                             HttpSession session,
                             Model model) {
        model.addAttribute("username", session.getAttribute("username"));
        model.addAttribute("tasksByStartDate", roadMapService.getTaskByMonth(year));
        model.addAttribute("year_to_show", year);
        return "road-map";
    }
}
