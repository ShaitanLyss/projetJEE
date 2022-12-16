package fr.cyu.airportmadness.validation.constraints.unique;

import jakarta.validation.ConstraintValidatorFactory;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

@Configuration
@EnableTransactionManagement
public class Config {
    @Bean
    public ConstraintValidatorFactory constraintValidatorFactory() {
        return new ConstraintValidatorFactoryImpl();
    }

    @Bean
    public ValidatorFactory validatorFactory() {
        return Validation.byDefaultProvider().configure()
                .constraintValidatorFactory(constraintValidatorFactory()).buildValidatorFactory();
    }

    @Bean
    public Validator validator1() {
        return validatorFactory().getValidator();
    }


    @Bean
    public LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }

//    @Bean
//    public MethodValidationPostProcessor methodValidationPostProcessor() {
//        MethodValidationPostProcessor bean = new MethodValidationPostProcessor();
//        bean.setValidator(Validation.byDefaultProvider()
//                .configure()
//                // We must use ParameterMessageInterpolator so that we do not need to add a dependency
//                // on Java Expression Language which is a potential security vulnerability.
//                // See https://securitylab.github.com/research/bean-validation-RCE/ for more info.
//                .messageInterpolator(new ParameterMessageInterpolator())
//                .buildValidatorFactory()
//                .getValidator());
//        return bean;
//    }
}
