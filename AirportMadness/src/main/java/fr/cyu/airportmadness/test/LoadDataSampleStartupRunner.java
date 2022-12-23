package fr.cyu.airportmadness.test;

import fr.cyu.airportmadness.entity.flight.FlightRepository;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;


@Component
public class LoadDataSampleStartupRunner {
    @Autowired
    TestController testController;

    Logger logger = LoggerFactory.getLogger(LoadDataSampleStartupRunner.class);
    @Autowired
    private FlightRepository flightRepository;

    @PostConstruct
    public void onPostConstruct() {
        if (flightRepository.count() == 0) {
            logger.info("No data in database. Loading data sample...");
            testController.loadTestSample();
        } else {
            logger.info("Data detected in database. Skipping loading of data sample.");
        }
    }




}
