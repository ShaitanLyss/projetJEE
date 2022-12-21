package fr.cyu.airportmadness.test;

import com.github.javafaker.Faker;
import fr.cyu.airportmadness.datasets.CsvAirport;
import fr.cyu.airportmadness.datasets.CsvCountry;
import fr.cyu.airportmadness.datasets.CsvUtils;
import fr.cyu.airportmadness.entity.aircraft.Aircraft;
import fr.cyu.airportmadness.entity.aircraft.AircraftRepository;
import fr.cyu.airportmadness.entity.airline.Airline;
import fr.cyu.airportmadness.entity.airline.AirlineRepository;
import fr.cyu.airportmadness.entity.airlinecompany.AirlineCompany;
import fr.cyu.airportmadness.entity.airlinecompany.AirlineCompanyRepository;
import fr.cyu.airportmadness.entity.airport.Airport;
import fr.cyu.airportmadness.entity.airport.AirportRepository;
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
import fr.cyu.airportmadness.entity.person.passenger.customer.CustomerRepository;
import fr.cyu.airportmadness.security.MyUserDetails;
import fr.cyu.airportmadness.security.User;
import fr.cyu.airportmadness.security.UserRepository;
import jakarta.persistence.*;
import jakarta.servlet.http.HttpServletResponse;
import org.locationtech.jts.geom.*;
import org.locationtech.jts.util.GeometricShapeFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Controller
public class TestController {
    private final AircraftRepository aircraftRepository;

    private final GeometricShapeFactory geometricShapeFactory = new GeometricShapeFactory();

    private final GeometryFactory geometryFactory = new GeometryFactory();

    @Autowired
    private AirportRepository airportRepository;
    private final BookingRepository bookingRepository;

    @Autowired
    private AirlineRepository airlineRepository;

    private final Logger logger = LoggerFactory.getLogger(TestController.class);
    @PersistenceContext
    private EntityManager em;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    private final CustomerRepository customerRepository;
    private final AirlineCompanyRepository airlineCompanyRepository;

    public TestController(AircraftRepository aircraftRepository, BookingRepository bookingRepository,
                          CustomerRepository customerRepository,
                          AirlineCompanyRepository airlineCompanyRepository) {
        this.aircraftRepository = aircraftRepository;
        this.bookingRepository = bookingRepository;
        this.customerRepository = customerRepository;
        this.airlineCompanyRepository = airlineCompanyRepository;
    }

    @GetMapping("/test")
    @ResponseBody
    public String test(
            @RequestParam(name = "lat") double lat, @RequestParam(name = "lon") double lon,
            @RequestParam(name = "tlat") double tlat, @RequestParam("tlon") double tlon
    ) {
//        return Arrays.toString(airportRepository.findAirportWithin(createCircle(lat, lon, radius)).toArray());
//        var it = airportRepository.findNearestAirports(lat, lon).iterator();
//        List<Airport> airports = new ArrayList<>(10);
//        for (int i = 0; i < 10 && it.hasNext(); i++) {
//            airports.add(it.next());
//        }
//
//        return Arrays.toString(airports.toArray());
//        return "";

        var it = airlineRepository.findNearestAirline(
                geometryFactory.createPoint(new Coordinate(lat, lon)),
                geometryFactory.createPoint(new Coordinate(tlat, tlon))
                );

        return it.iterator().next().toString();
    }

    private Geometry createCircle(Double latitude, Double longitude, Integer radius) {
        geometricShapeFactory.setNumPoints(32);
        geometricShapeFactory.setCentre(new Coordinate(latitude, longitude));
        geometricShapeFactory.setSize(radius * 2);
        return geometricShapeFactory.createCircle();
    }


    @GetMapping("/test/db")
    @ResponseBody
    public String testDb() {
        AtomicReference<String> res = new AtomicReference<>("");
        bookingRepository.findAll().forEach(booking -> res.updateAndGet(v -> v + booking.getPassengers()));
        return res.get();
    }


     private Iterable<Airport> loadAirportsAndCities() {
        logger.info("Loading airports, cities and countries...");
        if (airportRepository.count() > 0) {
            logger.warn("Airports are already loaded. Skipping");
            return airportRepository.findAll();
         }

        GeometryFactory geometryFactory = new GeometryFactory();

        // Load countries
        List<CsvCountry> csvCountries = CsvUtils.getCsvBeans("datasets/countries.csv", CsvCountry.class);
        Map<String, Country> codeToCountry = new HashMap<>(csvCountries.size());

        csvCountries.forEach((csvCountry -> {
            Country country = new Country().setName(csvCountry.getName());
            codeToCountry.put(csvCountry.getCode(), country);
            em.persist(country);
        }));

        AtomicInteger numAirports = new AtomicInteger();
        List<CsvAirport> csvAirports = CsvUtils.getCsvBeans("datasets/airports.csv", CsvAirport.class);
        csvAirports.stream().filter(
                (CsvAirport a) -> (Objects.equals(a.getType(), "large_airport") || Objects.equals(a.getType(), "medium_airport") && !a.getName().toLowerCase().contains(" base") && a.getMunicipality() != null && !Objects.equals(a.getMunicipality(), ""))
        ).forEach(csvAirport -> {
            Airport airport = new Airport();
            Country country = codeToCountry.get(csvAirport.getIso_country());

            if (csvAirport.getMunicipality() != null && !Objects.equals(csvAirport.getMunicipality(), "") && Objects.equals(csvAirport.scheduled_service, "yes")) {
                City city = new City().setName(csvAirport.getMunicipality()).setCountry(country);
                em.persist(city);
                airport.setCity(city);
            }
            airport
                    .setLatitude(csvAirport.getLatitude_deg())
                    .setLongitude(csvAirport.getLongitude_deg())
                    .setCountry(country)
                    .setName(csvAirport.getName())
                    .setType(csvAirport.getType());
            ;

            airport.setLocation(geometryFactory.createPoint(new Coordinate(airport.getLatitude(), airport.getLongitude())));
            em.persist(airport);

            int nAirports = numAirports.getAndIncrement();
            if (nAirports % 500 == 0)
                logger.info("" + nAirports + " airports loaded.");
        });

        em.flush();

        logger.info("Done loading airports, cities and countries : " + airportRepository.count() +  " airports loaded.");

        return airportRepository.findAll();
    }

    @GetMapping("/test/load-test-sample")
    @ResponseBody
    @Transactional
    public String loadTestSample(HttpServletResponse response) {
        logger.info("Loading test sample...");

        Faker faker = new Faker();
        var name = faker.name();
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        List<Object> toPersist = new ArrayList<>(10);

        // User
        if (userRepository.findByUsername("user") == null)
            userRepository.save(new User("user", passwordEncoder.encode("1234"), "USER"));

        if (userRepository.findByUsername("admin") == null)
            userRepository.save(new User("admin", passwordEncoder.encode("1234"), "ADMIN"));

        User airlineUser = userRepository.findByUsername("airline");

        if (airlineUser == null) {
            airlineUser = userRepository.save(new User("airline", passwordEncoder.encode("1234"), "AIRLINE"));
            toPersist.add(airlineUser);
        }


        // Aircrafts
        var numAircrafts = aircraftRepository.count();

        Aircraft ac1 = new Aircraft();
        ac1.setRegistration("a00" + (numAircrafts + 1));

        Aircraft ac2 = new Aircraft();
        ac2.setRegistration("a00" + (numAircrafts + 2));

        toPersist.add(ac1);
        toPersist.add(ac2);

        // Airport, Country, City
        loadAirportsAndCities();

        Airport paris = airportRepository.findByCity_NameContainsAndNameContains("paris", "international").get(0);
        Airport nsimalen = airportRepository.findByCity_NameContainsAndNameContains("yaoundé", "international").get(0);



        // Airline
        Airline airline1 = new Airline()
                .setDeparture(paris)
                .setArrival(nsimalen);

        Airline airline2 = new Airline()
                .setDeparture(nsimalen)
                .setArrival(paris);


        toPersist.add(airline1);
        toPersist.add(airline2);

        // Flights
        Flight flight1 = new Flight()
                .setAircraft(ac1)
                .setTime(LocalDateTime.of(2022, 11, 28, 17, 30))
                .setAirline(airline2);

        Flight flight2 = new Flight()
                .setAircraft(ac1)
                .setTime(LocalDateTime.of(2022, 11, 30, 17, 30))
                .setAirline(airline2);

        Flight flight3 = new Flight()
                .setAircraft(ac2)
                .setTime(LocalDateTime.of(2022, 11, 30, 10, 0))
                .setAirline(airline1);

        toPersist.addAll(Arrays.asList(flight1, flight2, flight3));

        if (bookingRepository.count() == 0) {
            // Customer
            Customer sylvie = new Customer(
                    "Sylvie", "Delprat", LocalDate.of(1980, 7, 3),
                    Gender.Female, "française", "FR12456987", PaperType.IdentityCard,
                    "0611223344", "sylvie@sylvie.fr"
            );

            toPersist.add(sylvie);

            // Passenger
            Passenger florian = new Passenger(
                    "Florian", "Delprat", LocalDate.of(2000, 9, 30),
                    Gender.Male, "française", "FR987654321", PaperType.Passport);
            Passenger marine = new Passenger(
                    "Marine", "Delprat", LocalDate.of(2004, 8, 17),
                    Gender.Female, "française", "FR987654321", PaperType.ResidenceDocument);

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
        }

        // Airline Company
        AirlineCompany comp = new AirlineCompany()
                .addAircrafts(ac1, ac2)
                .addAirlines(airline1, airline2)
                .setName("Air Cameroun " + airlineCompanyRepository.count() + 1);

        if (airlineUser.getAirlineCompany() == null)
                comp.setUser(airlineUser);

        toPersist.add(comp);

        // Employees
        for (int i = 0; i < 100; i++) {
            Employee employee = new Employee();
            employee
                    .setAirlineCompany(comp)
                    .setGender(Math.random() > 0.5 ? Gender.Male : Gender.Female)
                    .setFirstName(name.firstName())
                    .setLastName(name.lastName())
                    .setBirthdate(faker.date().birthday(10, 90).toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                    .setNationality(faker.nation().nationality())

            ;
            toPersist.add(employee);
        }


        toPersist.forEach((o) -> em.persist(o));

        logger.info("Loading complete");

        return "Yay.";
    }


    @GetMapping("/test/user-status")
    @ResponseBody
    public String userStatus(Authentication authentication) {
        String res = "";

        if (authentication != null) {
            MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
            res += "Nom : " + userDetails.getUsername() + "<br>";
            res += "Rôles : " + userDetails.getAuthorities();
        } else {
            res += "L'utilisateur n'est pas connecté !";
        }

        return res;
    }

    @Override
    public int hashCode() {
        return Objects.hash(aircraftRepository, bookingRepository, em, userRepository, passwordEncoder);
    }
}
