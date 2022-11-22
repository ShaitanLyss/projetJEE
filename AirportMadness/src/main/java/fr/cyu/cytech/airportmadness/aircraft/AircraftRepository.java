package fr.cyu.cytech.airportmadness.aircraft;


import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource()
public interface AircraftRepository extends PagingAndSortingRepository<Aircraft, Long> {
    List<Aircraft> findByRegistration(@Param("registration") String registration);
}
