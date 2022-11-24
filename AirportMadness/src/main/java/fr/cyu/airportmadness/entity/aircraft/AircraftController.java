package fr.cyu.airportmadness.entity.aircraft;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class AircraftController {
    @Autowired
    private AircraftRepository aircraftRepository;


    /**
     * Crée un avion.
     * @param registration enregistrement de l'avion
     * @return l'avion créé
     */
    @GetMapping("/aircrafto")
    public String aircraft(@RequestParam(value = "registration") String registration) {
        var aircraft = new Aircraft();
        aircraft.setRegistration(registration);
        aircraftRepository.save(aircraft);
        return "index";
    }
}
