package fr.cyu.airportmadness.airline;

import fr.cyu.airportmadness.entity.aircraft.AircraftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AirlineController {
    @Autowired
    private AircraftRepository aircraftRepository;


    @GetMapping("/airline")
    public String airlineDashboard(Model model) {
        model.addAttribute("aircrafts", aircraftRepository.findAll());
        return "airline/index";
    }
}
