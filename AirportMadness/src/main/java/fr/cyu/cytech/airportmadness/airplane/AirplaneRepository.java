package fr.cyu.cytech.airportmadness.airplane;


import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource()
public interface AirplaneRepository extends PagingAndSortingRepository<Airplane, Long> {
    List<Airplane> findByRegistration(@Param("registration") String registration);
}
