package fr.cyu.airportmadness.view.thymeleaf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;

@Configuration
public class ThymeleafConfig {
    @Bean
    public ThymeleafViewResolver thymeleafViewResolver(@Autowired SpringTemplateEngine templateEngine ) {
        ThymeleafViewResolver thymeleafViewResolver = new ThymeleafViewResolver();
        thymeleafViewResolver.setTemplateEngine( templateEngine );
        thymeleafViewResolver.setCharacterEncoding( "UTF-8" );
        thymeleafViewResolver.addStaticVariable( "encore", EncoreHelpers.getInstance());
        return thymeleafViewResolver;
    }
}
