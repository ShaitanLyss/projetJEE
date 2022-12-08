package fr.cyu.airportmadness.security;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;


@Configuration
@EnableWebSecurity
public class AirportMadnessConfigSecurity {
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider  authenticationProvider() {
        DaoAuthenticationProvider authProvider  = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

//
//
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        Dotenv env  = Dotenv.load();
        http.authorizeHttpRequests()
                .requestMatchers("/admin").hasRole("ADMIN")
                .requestMatchers("/user").hasRole("USER")
                .requestMatchers("/api/**").permitAll()
                .requestMatchers("/", "/saveUser", "/save-user", "/error").permitAll()
                .requestMatchers("/test/**").permitAll()
                .requestMatchers("/build/**", "/error/**").permitAll()
                .and()
                .formLogin()
                .and()
                .rememberMe()
                .key(env.get("SECURITY_KEY", "SET_IN_ENV"))

                // Add CSRF token to http response
                .and().csrf().disable()
//        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())

                // Oauth2
 //                .and()
//                .oauth2Login()
        ;


        return http.build();
    }
//
//
}
