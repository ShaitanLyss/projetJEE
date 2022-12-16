package fr.cyu.airportmadness.validation.constraints.unique;

import fr.cyu.airportmadness.security.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.ConstraintValidatorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

public class UniqueValidator implements ConstraintValidator<Unique, Serializable>, EntityManagerAwareValidator {
//    @Autowired
//    private EntityManagerFactory entityManager;

//    @Override
//    public void setEntityManager(EntityManager entityManager) {
//        this.entityManager = entityManager;
//    }

    @Autowired
    private ConstraintValidatorFactory userRepository;
    private EntityManager entityManager;

    private Class<?> entityClass;
    private String key;


    @Override
    public void initialize(Unique constraintAnnotation) {
        this.key = constraintAnnotation.key();
        this.entityClass = constraintAnnotation.entityClass();

    }

    @Override
    public boolean isValid(Serializable target, ConstraintValidatorContext context) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Object> criteriaQuery = criteriaBuilder.createQuery();

        Root<?> root = criteriaQuery.from(entityClass);


//        String propertyName = key;
//        PropertyDescriptor desc = new PropertyDescriptor(propertyName, entityClass);
//        Method readMethod = desc.getReadMethod();
//        Object propertyValue = readMethod.invoke(target);
//        Predicate predicate = criteriaBuilder.equal(root.get(propertyName), propertyValue);
//        predicates.add(predicate);


        criteriaQuery.where(criteriaBuilder.equal(root.get(key), target));

        TypedQuery<Object> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Object> resultSet = typedQuery.getResultList();

        return resultSet.size() == 0;
    }

    @Override
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
