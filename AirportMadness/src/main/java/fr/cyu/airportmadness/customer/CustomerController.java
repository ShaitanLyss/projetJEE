package fr.cyu.airportmadness.customer;

import fr.cyu.airportmadness.entity.airline.Airline;
import fr.cyu.airportmadness.entity.airline.AirlineRepository;
import fr.cyu.airportmadness.entity.airlinecompany.AirlineCompany;
import fr.cyu.airportmadness.entity.booking.Booking;
import fr.cyu.airportmadness.entity.booking.BookingRepository;
import fr.cyu.airportmadness.entity.person.passenger.PassengerRepository;
import fr.cyu.airportmadness.entity.person.passenger.customer.Customer;
import fr.cyu.airportmadness.entity.person.passenger.customer.CustomerRepository;
import fr.cyu.airportmadness.security.MyUserDetails;
import fr.cyu.airportmadness.security.User;
import fr.cyu.airportmadness.security.UserRepository;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.assertj.core.util.Arrays;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
public class CustomerController {
    private final AirlineRepository airlineRepository;
    private final Logger logger = LoggerFactory.getLogger(CustomerController.class);
    private final GeometryFactory geometryFactory = new GeometryFactory();
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public CustomerController(AirlineRepository airlineRepository,
                              CustomerRepository customerRepository,
                              UserRepository userRepository,
                              BookingRepository bookingRepository,
                              PassengerRepository passengerRepository) {
        this.airlineRepository = airlineRepository;
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
        this.passengerRepository = passengerRepository;
    }

    @GetMapping("/booking")
    public String bookingForm(
            Model model,
            @RequestParam(value = "lat", required = false) Double lat,
            @RequestParam(value = "lon", required = false) Double lon,
            @RequestParam(value = "q", required = false) String q,
            @RequestParam(value = "tlat", required = false) Double tlat,
            @RequestParam(value = "tlon", required = false) Double tlon,
            @RequestParam(value = "tq", required = false) String tq,
            Authentication authentication, HttpServletRequest request
            ) {

        model.addAttribute("redirectUrl", request.getRequestURI() + "?" + request.getQueryString());
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

            var customer = getCustomer(authentication);
            model.addAttribute(
                    "customer", customer.orElseGet(Customer::new)
            );
            if (customer.isEmpty())
                model.addAttribute("user", new User());
        }
        return "customer/booking";
    }

    @PostMapping("/booking")
    public String bookingSubmit(@ModelAttribute Booking booking, @ModelAttribute("redirectUrl") String redirectUrl, Authentication auth, RedirectAttributes redirectAttributes) {
        Optional<Customer> opt_customer = getCustomer(auth);

        if (opt_customer.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Échec. L'utilisateur n'est pas connecté avec un compte client");
            return "redirect:" + redirectUrl;
        }

        Customer customer = opt_customer.get();
        booking.setCustomer(customer);
        booking.addPassenger(customer);

        bookingRepository.save(booking);

        opt_customer = customerRepository.findById(customer.getId());

        if (opt_customer.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Échec. Problème d'accès à la base de données.");
            return "redirect:" + redirectUrl;
        }
        customer = opt_customer.get();
        redirectAttributes.addFlashAttribute("message", "Succès. Vous avez maintenant : " + customer.getBookings().size() + " réservations.");

        return "redirect:/booking";
    }

    @Autowired
    private AuthenticationManager authenticationManager;
    private final BookingRepository bookingRepository;
    private final PassengerRepository passengerRepository;

    @PostMapping("/booking/create-customer")
    public String createCustomer(HttpServletRequest req, @ModelAttribute("redirectUrl") String redirectUrl, @ModelAttribute Customer customer, @ModelAttribute User user) {

        Dotenv dotenv = Dotenv.load();
        customerRepository.save(customer);

        String password = user.getPassword();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER");
        user.setPerson(customer);
        userRepository.save(user);
        UserDetails userDetails = new MyUserDetails(user);
        Authentication authReq = new UsernamePasswordAuthenticationToken(user.getUsername(), password, userDetails.getAuthorities());
        Authentication auth = authenticationManager.authenticate(authReq);
        SecurityContextHolder.setContext(new SecurityContextImpl(auth));
        SecurityContextHolder.getContext().setAuthentication(auth);
        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);
        HttpSession session = req.getSession(true);
        session.getAttributeNames().asIterator().forEachRemaining(System.out::println);
        session.setAttribute("SPRING_SECURITY_CONTEXT", sc);


        return "redirect:" + redirectUrl;
    }

    @GetMapping(name = "welcome-page", value = "/")
    public String welcomePage(Authentication authentication, Model model) {

        if(authentication != null){
            MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
            model.addAttribute("user", userDetails);
        }


        return "index";
    }

    @RequestMapping("/bookings")
    public String displayBookings(Authentication auth, Model model) {
        Optional<Customer> customer = getCustomer(auth);
        customer.ifPresent(value -> model.addAttribute("customer", value));

        return "customer/bookings";
    }

    @DeleteMapping("/bookings/{id}")
    public String deleteBooking(@PathVariable("id") Long id, RedirectAttributes redirectAttributes){
        Optional<Booking> opt_booking = bookingRepository.findById(id);

        if (opt_booking.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Échec suppression. Réservation non trouvée.");
            return "redirect:/bookings";
        }
        Booking booking = opt_booking.get();

        booking.getPassengers().forEach((p) -> {
            p.removeBooking(booking);
            passengerRepository.save(p);
        });
        Customer customer = booking.getCustomer();
        customer.removeCreatedBooking(booking);
        customerRepository.save(customer);

//        bookingRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Succès. Une réservation supprimée.");

        return "redirect:/bookings";
    }


    private Optional<Customer> getCustomer(Authentication authentication) {
        if (authentication == null)
            return Optional.empty();

        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        if (userDetails.getUser().getPerson() == null)
            return Optional.empty();

        return customerRepository.findById(userDetails.getUser().getPerson().getId());
    }
}
