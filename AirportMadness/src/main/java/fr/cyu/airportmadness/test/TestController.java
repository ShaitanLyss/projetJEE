package fr.cyu.airportmadness.test;

import fr.cyu.airportmadness.entity.aircraft.Aircraft;
import fr.cyu.airportmadness.entity.aircraft.AircraftRepository;
import fr.cyu.airportmadness.entity.airline.Airline;
import fr.cyu.airportmadness.entity.airlinecompany.AirlineCompany;
import fr.cyu.airportmadness.entity.airport.Airport;
import fr.cyu.airportmadness.entity.booking.Booking;
import fr.cyu.airportmadness.entity.booking.BookingRepository;
import fr.cyu.airportmadness.entity.city.City;
import fr.cyu.airportmadness.entity.country.Country;
import fr.cyu.airportmadness.entity.flight.Flight;
import fr.cyu.airportmadness.entity.person.Gender;
import fr.cyu.airportmadness.entity.person.employee.Employee;
import fr.cyu.airportmadness.entity.person.passenger.PaperType;
import fr.cyu.airportmadness.entity.person.passenger.Passenger;
import fr.cyu.airportmadness.entity.person.passenger.customer.Customer;
import jakarta.persistence.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Controller
public class TestController {
    private final AircraftRepository aircraftRepository;
    private final BookingRepository bookingRepository;
    @PersistenceContext
    private EntityManager em;

    public TestController(AircraftRepository aircraftRepository, BookingRepository bookingRepository) {
        this.aircraftRepository = aircraftRepository;
        this.bookingRepository = bookingRepository;
    }

    @GetMapping("/test")
    public String test(@RequestParam(name = "name", defaultValue = "Company") String name, Model model) {
        model.addAttribute("name", name);
        return "index";
    }

    @GetMapping("/test/db")
    @ResponseBody
    public String testDb() {
        AtomicReference<String> res = new AtomicReference<>("");
        bookingRepository.findAll().forEach(booking -> res.updateAndGet(v -> v + booking.getPassengers()));
        return res.get();
    }

    @GetMapping("/test/load-test-sample")
    @ResponseBody
    @Transactional
    public String loadTestSample(HttpServletResponse response) {
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        
        List<Object> toPersist = new ArrayList<>(10);

        // Aircrafts
        var numAircrafts = aircraftRepository.count();

        Aircraft ac1 = new Aircraft();
        ac1.setRegistration("a00" + (numAircrafts + 1));

        Aircraft ac2 = new Aircraft();
        ac2.setRegistration("a00" + (numAircrafts + 2));

        toPersist.add(ac1);
        toPersist.add(ac2);
        
        // Employees
        Employee employee1 = new Employee();
        employee1
                .setGender(Gender.Male)
                .setFirstName("Bob")
                .setLastName("Marley")
                .setBirthdate(LocalDate.parse("2000-09-30"))
                .setNationality("French");


        Employee employee2 = new Employee();
        employee2
                .setGender(Gender.female)
                .setFirstName("Alice")
                .setLastName("Windmill")
                .setBirthdate(LocalDate.parse("1997-05-23"))
                .setNationality("Cameroonian");

        toPersist.add(employee1);
        toPersist.add(employee2);

        // Country
        Country france = new Country("france");
        Country cameroon = new Country("cameroon");

        toPersist.add(france);
        toPersist.add(cameroon);

        // City
        City yaounde = new City("yaoundé")
                .setCountry(cameroon);

        City paris = new City("paris")
                .setCountry(france);

        toPersist.add(yaounde);
        toPersist.add(paris);


        // Airport
        Airport nsimalen = new Airport()
                .setName("nsimalen")
                .setCity(yaounde);


        Airport parisRoissy = new Airport()
                .setName("Paris Roissy")
                .setCity(paris);

        toPersist.add(nsimalen);
        toPersist.add(parisRoissy);


        // Airline
        Airline nsimalenParisRoissy = new Airline()
                .setDeparture(nsimalen)
                .setArrival(parisRoissy);

        Airline parisRoissyNsimalen = new Airline()
                .setDeparture(parisRoissy)
                .setArrival(nsimalen);

        toPersist.add(nsimalenParisRoissy);
        toPersist.add(parisRoissyNsimalen);

        // Flights
        Flight flight1 = new Flight()
                .setAircraft(ac1)
                .setTime(LocalDateTime.of(2022,11,28,17,30))
                .setAirline(parisRoissyNsimalen);

        Flight flight2 = new Flight()
                .setAircraft(ac1)
                .setTime(LocalDateTime.of(2022,11,30,17,30))
                .setAirline(parisRoissyNsimalen);

        Flight flight3 = new Flight()
                .setAircraft(ac2)
                .setTime(LocalDateTime.of(2022,11,30,10,0))
                .setAirline(nsimalenParisRoissy);

        toPersist.addAll(Arrays.asList(flight1, flight2, flight3));

        // Customer
        Customer sylvie = new Customer(
                "Sylvie", "Delprat", LocalDate.of(1980, 7, 3),
                Gender.female, "française", "FR12456987", PaperType.IdentityCard,
                "0611223344", "sylvie@sylvie.fr"
                );

        toPersist.add(sylvie);

        // Passenger
        Passenger florian = new Passenger(
                "Florian", "Delprat", LocalDate.of(2000,9,30),
                Gender.Male, "française", "FR987654321", PaperType.Passport);
        Passenger marine = new Passenger(
                "Marine", "Delprat", LocalDate.of(2004,8,17),
                Gender.female, "française", "FR987654321", PaperType.ResidenceDocument);

        toPersist.add(florian);
        toPersist.add(marine);

        // Bookings
        Booking booking = new Booking()
                .setCustomer(sylvie)
                .setPrice(BigDecimal.valueOf(140))
                .setFlight(flight2)
                .setNumLuggages(4)
                .addPassengers(sylvie, florian, marine);

        toPersist.add(booking);


        // Airline Company
        AirlineCompany comp = new AirlineCompany()
                .addAircrafts(ac1, ac2)
                .addAirlines(nsimalenParisRoissy, parisRoissyNsimalen)
                .setName("Air Cameroun")
                .addEmployees(employee1, employee2);


        toPersist.add(comp);


        toPersist.forEach((o) -> em.persist(o));

        return "Yay.";
    }
}
