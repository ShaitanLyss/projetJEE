package fr.cyu.airportmadness.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
    @GetMapping(name = "admin-dashboard", value = "/admin")
    public String adminDashboard() {
        return "admin/index";
    }
}
