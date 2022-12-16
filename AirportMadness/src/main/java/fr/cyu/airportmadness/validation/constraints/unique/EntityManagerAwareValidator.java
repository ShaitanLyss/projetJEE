package fr.cyu.airportmadness.validation.constraints.unique;


import jakarta.persistence.EntityManager;

public interface EntityManagerAwareValidator {
    void setEntityManager(EntityManager entityManager);
}

