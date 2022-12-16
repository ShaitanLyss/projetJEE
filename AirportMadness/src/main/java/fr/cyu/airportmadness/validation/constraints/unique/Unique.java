package fr.cyu.airportmadness.validation.constraints.unique;

import fr.cyu.airportmadness.security.User;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = UniqueValidator.class)
@Documented
//@Repeatable(List.class)
public @interface Unique {

    String message() default "doit être unique";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    String key();


    Class<?> entityClass();
}
