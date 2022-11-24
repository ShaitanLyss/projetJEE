package fr.cyu.airportmadness.test;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TestController {
    @GetMapping("/test")
    public String test(@RequestParam(name="name", defaultValue="Company") String name, Model model) {
        model.addAttribute("name", name);
        return "index";
    }
}
