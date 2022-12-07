package fr.cyu.airportmadness.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

//
//import com.mysql.cj.jdbc.MysqlDataSource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.provisioning.JdbcUserDetailsManager;
//import org.springframework.security.provisioning.UserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//
//import javax.sql.DataSource;
//
//@Configuration
//@EnableWebSecurity
@Configuration
@EnableWebSecurity
public class AirportMadnessConfigSecurity {
//
//    //@Autowired
//    //PasswordEncoder passwordEncoder;
//    @Autowired
//    UserRepository userRepository;
//
//    /**
//     * @Autowired private DataSource dataSource;
//     * @Autowired UserRepository userRepository;
//     * @Override public void configure(AuthenticationManagerBuilder auth)
//     * throws Exception {
//     * userRepository.findAll().forEach(user ->
//     * {
//     * try {
//     * auth.jdbcAuthentication()
//     * .dataSource(dataSource)
//     * .usersByUsernameQuery(user.getUsername())
//     * .authoritiesByUsernameQuery(user.getUsername());
//     * } catch (Exception e) {
//     * throw new RuntimeException(e);
//     * }
//     * }
//     * <p>
//     * )
//     * ;
//     * <p>
//     * }
//     **/
//
//    @Bean
//    public DataSource dataSource() {
//        return new MysqlDataSource();
//    }
//
//    /*@Bean
//    public UserDetailsManager userDetailsService()  {
//        return new JdbcUserDetailsManager()
//    }*/
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .requestMatchers("/admin").hasRole("ADMIN")
                .requestMatchers("/user").hasRole("USER")
                .requestMatchers("/").permitAll()
//                .anyRequest().permitAll()
                .and()
                .formLogin()
//                .and()
//                .oauth2Login()
        ;
        return http.build();
    }
//
//
}
