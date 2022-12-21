package fr.cyu.airportmadness.customer;

import fr.cyu.airportmadness.entity.airline.Airline;
import fr.cyu.airportmadness.entity.airline.AirlineRepository;
import fr.cyu.airportmadness.entity.booking.Booking;
import fr.cyu.airportmadness.security.MyUserDetails;
import org.assertj.core.util.Arrays;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class CustomerController {
    private final AirlineRepository airlineRepository;
    private final Logger logger = LoggerFactory.getLogger(CustomerController.class);
    private final GeometryFactory geometryFactory = new GeometryFactory();

    public CustomerController(AirlineRepository airlineRepository) {
        this.airlineRepository = airlineRepository;
    }

    @GetMapping("/booking")
    public String bookingForm(
            Model model,
            @RequestParam(value = "lat", required = false) Double lat,
            @RequestParam(value = "lon", required = false) Double lon,
            @RequestParam(value = "q", required = false) String q,
            @RequestParam(value = "tlat", required = false) Double tlat,
            @RequestParam(value = "tlon", required = false) Double tlon,
            @RequestParam(value = "tq", required = false) String tq
            ) {
        List<Object> params = Arrays.asList(new Object[]{lat, lon, tlat, tlon});

        if (params.stream().allMatch(Objects::nonNull)) {
            Point location = geometryFactory.createPoint(new Coordinate(lat, lon));
            Point destination = geometryFactory.createPoint(new Coordinate(tlat, tlon));

            Airline bestAirline = airlineRepository.findNearestAirline(location, destination).iterator().next();
            model.addAttribute("airline", bestAirline);
            logger.info("Found airline : " + bestAirline + " with " + bestAirline.getFlights().size() + " flights.");

            model.addAttribute(
                    "booking", new Booking()
                            .setNumLuggages(0)
                            .setPrice(BigDecimal.valueOf(100))
            );
        }
        return "customer/booking";
    }

    @PostMapping("/booking")
    public String bookingSubmit(@ModelAttribute Booking booking) {


        return "redirect:/";
    }

    @GetMapping(name = "welcome-page", value = "/")
    public String welcomePage(Authentication authentication, Model model) {

        if(authentication != null){
            MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
            model.addAttribute("user", userDetails);
        }


        return "index";
    }

}
