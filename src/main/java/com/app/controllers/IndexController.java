package com.app.controllers;

import com.app.security.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;


@Controller
public class IndexController {
    @GetMapping("/")
    public String getStartPage(HttpSession session) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof CustomUserDetails) {
            String username = ((CustomUserDetails) principal).getUsername();
            session.setAttribute("username", username);
        } else {
            String username = principal.toString();
            session.setAttribute("username", username);
        }

        if (session.getAttribute("username").toString().equals("anonymousUser")) {
            return "index";
        } else {
            return "redirect:/menu";
        }
    }
}
