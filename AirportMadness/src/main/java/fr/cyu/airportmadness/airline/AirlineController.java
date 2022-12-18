package fr.cyu.airportmadness.airline;

import fr.cyu.airportmadness.entity.aircraft.Aircraft;
import fr.cyu.airportmadness.entity.aircraft.AircraftRepository;
import fr.cyu.airportmadness.entity.airlinecompany.AirlineCompany;
import fr.cyu.airportmadness.entity.airlinecompany.AirlineCompanyRepository;
import fr.cyu.airportmadness.entity.flight.Flight;
import fr.cyu.airportmadness.entity.flight.FlightRepository;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Arrays;
import java.util.Objects;

@Controller
public class AirlineController {
    @Autowired
    private AircraftRepository aircraftRepository;

    @Autowired
    private AirlineCompanyRepository airlineCompanyRepository;
    @Autowired
    private FlightRepository flightRepository;


    @GetMapping(name = "airline-dashboard", value = "/airline")
    public String airlineDashboard(Model model) {
        AirlineCompany airlineCompany = airlineCompanyRepository.findAll().iterator().next();
        model
                .addAttribute("aircraft", new Aircraft())
                .addAttribute("flight", new Flight())
                .addAttribute("airlineCompany", airlineCompany)
        ;
        return "airline/index";
    }

    @PostMapping(name = "creation-aircraft", value = "/airline/create_aircraft")
    public String saveAircraft(@ModelAttribute Aircraft aircraft, RedirectAttributes redirectAttributes) {
        AirlineCompany airlineCompany = airlineCompanyRepository.findAll().iterator().next();
        aircraft.setOwningAirlineCompany(airlineCompany);

        String msg = "Succès !";
        try {
            aircraftRepository.save(aircraft);
        } catch (DataIntegrityViolationException e) {
            msg = "Échec. " + Objects.requireNonNull(e.getRootCause()).getLocalizedMessage();
        }
        redirectAttributes.addFlashAttribute("message", msg);


        return "redirect:/airline";
    }

    @PostMapping(name = "creation-flight", value = "/airline/create_fight")
    public String saveFlight(@ModelAttribute Flight flight, RedirectAttributes redirectAttributes) {

        flightRepository.save(flight);
        redirectAttributes.addFlashAttribute("message", "Success");


        return "redirect:/airline";
    }
}
