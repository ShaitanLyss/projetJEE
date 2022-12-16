package fr.cyu.airportmadness.validation.constraints.unique;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorFactory;

public class ConstraintValidatorFactoryImpl implements ConstraintValidatorFactory {

    @PersistenceContext
    private EntityManager entityManager;



//    public ConstraintValidatorFactoryImpl(EntityManagerFactory entityManagerFactory) {
//        this.entityManagerFactory = entityManagerFactory;
//    }

    @Override
    public <T extends ConstraintValidator<?, ?>> T getInstance(Class<T> key) {
        T instance = null;

        try {
            instance = key.newInstance();
        } catch (Exception e) {
            // could not instantiate class
            e.printStackTrace();
        }

        if(EntityManagerAwareValidator.class.isAssignableFrom(key)) {
            EntityManagerAwareValidator validator = (EntityManagerAwareValidator) instance;
            validator.setEntityManager(entityManager);
        }

        return instance;
    }

    @Override
    public void releaseInstance(ConstraintValidator<?, ?> constraintValidator) {

    }
}
