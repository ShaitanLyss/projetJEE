package fr.cyu.airportmadness;

import fr.cyu.airportmadness.security.SecurityController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class AirportMadnessApplicationTests {
    @Autowired
    private SecurityController controller;

    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
    }

}
