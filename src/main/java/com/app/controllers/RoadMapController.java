package com.app.controllers;

import com.app.services.RoadMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class RoadMapController {
    @Autowired
    private RoadMapService roadMapService;

    private List<Integer> years = new ArrayList<Integer>();
    private final List<String> months = Arrays.asList("January", "February", "March", "April", "May", "June", "July",
            "August", "September", "October", "November", "December");

    @GetMapping("/road-map")
    public String roadMap(HttpSession session, Model model) {
        model.addAttribute("username", session.getAttribute("username"));
        model.addAttribute("tasksByStartDate", roadMapService.getTaskByMonth(LocalDateTime.now().getYear(), months));
        model.addAttribute("year_to_show", LocalDateTime.now().getYear());
        return "road-map";
    }

    @GetMapping("/road-map/{year}")
    public String getRoadMap(@PathVariable Integer year,
                             HttpSession session,
                             Model model) {
        model.addAttribute("username", session.getAttribute("username"));
        model.addAttribute("tasksByStartDate", roadMapService.getTaskByMonth(year, months));
        model.addAttribute("year_to_show", year);
        return "road-map";
    }
}
