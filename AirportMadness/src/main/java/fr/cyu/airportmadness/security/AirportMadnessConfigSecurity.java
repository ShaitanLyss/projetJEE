package fr.cyu.airportmadness.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class AirportMadnessConfigSecurity extends WebSecurityConfigurerAdapter {

    //@Autowired
    //PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;
/**
     @Autowired
    private DataSource dataSource;
    @Autowired
    UserRepository userRepository;

    @Override
    public void configure(AuthenticationManagerBuilder auth)
            throws Exception {
                userRepository.findAll().forEach(user ->
                        {
                            try {
                                auth.jdbcAuthentication()
                                .dataSource(dataSource)
                                        .usersByUsernameQuery(user.getUsername())
                                        .authoritiesByUsernameQuery(user.getUsername());
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }

                )
                ;

    }
**/

    @Override
    protected  void configure(AuthenticationManagerBuilder auth) throws Exception{
        userRepository.findAll().forEach(user ->
            {
                try {
                    auth.inMemoryAuthentication()
                            .withUser(user.getUsername()).password(passwordEncoder().encode(user.getPassword())).roles(user.getRole())
                            ;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
         });
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/user").hasRole("USER")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .oauth2Login()
                ;
    }


}
