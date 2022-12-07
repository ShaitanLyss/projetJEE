package fr.cyu.airportmadness;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
//import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
//import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
//import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AirportWebAppTests {

//    @Autowired
//    private MockMvc mock;
//    @Autowired
//    WebApplicationContext context;
//
//    @Test
//    public void souldReturnDefaultMessage() throws Exception{
//        mock.perform(get("/login")).andDo(print()).andExpect(status().isOk());
//    }
//    @Test
//    public void userLoginTest() throws Exception{
//        mock.perform(formLogin("/login")
//                .user("rodriguez").password("toto")).andExpect(authenticated());
//    }
//    @Test
//    public void userLoginFail() throws Exception{
//        mock.perform(formLogin("/login")
//                .user("rodriguez").password("tota")).andExpect(unauthenticated());
//    }
//
//    @BeforeEach
//    public void setup(){
//        mock = MockMvcBuilders
//                .webAppContextSetup(context)
//                .apply(springSecurity())
//                .build();
//    }

}
