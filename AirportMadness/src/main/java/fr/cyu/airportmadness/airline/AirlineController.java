package fr.cyu.airportmadness.airline;

import fr.cyu.airportmadness.entity.aircraft.Aircraft;
import fr.cyu.airportmadness.entity.aircraft.AircraftRepository;
import fr.cyu.airportmadness.entity.airlinecompany.AirlineCompany;
import fr.cyu.airportmadness.entity.airlinecompany.AirlineCompanyRepository;
import fr.cyu.airportmadness.entity.city.City;
import fr.cyu.airportmadness.entity.city.CityRepository;
import fr.cyu.airportmadness.entity.flight.Flight;
import fr.cyu.airportmadness.entity.flight.FlightRepository;
import fr.cyu.airportmadness.security.MyUserDetails;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.Session;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.*;

import static java.lang.Integer.parseInt;

@Controller
public class AirlineController {
    @Autowired
    private AircraftRepository aircraftRepository;

    @Autowired
    private AirlineCompanyRepository airlineCompanyRepository;
    @Autowired
    private FlightRepository flightRepository;
    @Autowired
    private CityRepository cityRepository;


    private Optional<AirlineCompany> getAirlineCompany(Authentication authentication) {
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();

        return airlineCompanyRepository.findById(userDetails.getUser().getAirlineCompany().getId());
    }

    @GetMapping(name = "airline-dashboard", value = "/airline")
    public String airlineDashboard(Model model, Authentication authentication) {

        if (authentication == null) {
            return "redirect:/login";
        }

        Optional<AirlineCompany> opt_airlineCompany = getAirlineCompany(authentication);

        // Cas où l'utilisateur n'est pas associé à une compagnie
        // aérienne
        if (opt_airlineCompany.isEmpty()) {
            return "redirect:/login";
        }

        AirlineCompany airlineCompany = opt_airlineCompany.get();
        Iterator<City> towns =  cityRepository.findAll().iterator();
        Iterator<City> towns2 =  cityRepository.findAll().iterator();

        model
                .addAttribute("aircraft", new Aircraft())
                .addAttribute("flight", new Flight())
                .addAttribute("towns", towns)
                .addAttribute("towns2", towns2)
                .addAttribute("airlineCompany", airlineCompany)
        ;
        return "airline/index";
    }

    @PostMapping(name = "creation-aircraft", value = "/airline/create_aircraft")
    public String saveAircraft(@ModelAttribute Aircraft aircraft, Authentication authentication, RedirectAttributes redirectAttributes) {
        String msg = "Succès !";

        Optional<AirlineCompany> opt_airlineCompany = getAirlineCompany(authentication);

        if (opt_airlineCompany.isEmpty()) {
            msg = "Échec. Ce compte n'est pas associé à une compagnie aérienne";

        } else {
            AirlineCompany airlineCompany = opt_airlineCompany.get();
            aircraft.setOwningAirlineCompany(airlineCompany);

            try {
                aircraftRepository.save(aircraft);
            } catch (DataIntegrityViolationException e) {
                msg = "Échec. " + Objects.requireNonNull(e.getRootCause()).getLocalizedMessage();
            }
        }

        redirectAttributes.addFlashAttribute("message", msg);


        return "redirect:/airline";
    }

    @PostMapping(name = "creation-flight", value = "/airline/create_flight")
    public String saveFlight(@ModelAttribute Flight flight, RedirectAttributes redirectAttributes, HttpServletRequest request) throws ServletException, IOException {
       Optional<Aircraft> avion = aircraftRepository.findById(Long.valueOf(request.getParameter("flight-select-aircraft")));
       System.out.println("le parametre recuperer : "+ request.getParameter("flight-select-aircraft"));
        Aircraft sky_fly = avion.get();
        sky_fly.getFlights().add(flight);
        flightRepository.save(flight);
        aircraftRepository.save(sky_fly);
        redirectAttributes.addFlashAttribute("message", "Success");


        return "redirect:/airline";
    }
}
