package fr.cyu.cytech.airportmadness.aircraft;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AircraftController {
    @Autowired
    private AircraftRepository aircraftRepository;



    @GetMapping("/aircrafto")
    public Aircraft aircraft(@RequestParam(value = "registration") String registration) {
        var aircraft = new Aircraft();
        aircraft.setRegistration(registration);
        aircraftRepository.save(aircraft);
        return aircraft;
    }
}
