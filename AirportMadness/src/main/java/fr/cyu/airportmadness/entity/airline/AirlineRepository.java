package fr.cyu.airportmadness.entity.airline;

import fr.cyu.airportmadness.entity.flight.Flight;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AirlineRepository extends CrudRepository<Airline, Long> {
    @Query("select a from Airline a order by distance(a.departure.location, :pos) + distance(a.arrival.location, :dest)")
    Iterable<Airline> findNearestAirline(@Param("pos") Point position, @Param("dest") Point destination);
}