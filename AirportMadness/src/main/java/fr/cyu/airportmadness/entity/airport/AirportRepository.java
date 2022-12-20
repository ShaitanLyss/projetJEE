package fr.cyu.airportmadness.entity.airport;

import org.locationtech.jts.geom.Geometry;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AirportRepository extends CrudRepository<Airport, Long> {
    @Query("SELECT r FROM Airport r WHERE within(r.location, :filter) = true")
    List<Airport> findAirportWithin(@Param("filter") Geometry filter);

    @Query("SELECT a from Airport a order by 6371 * acos(cos(radians(:lat)) *  cos(radians(a.latitude)) *   cos(radians(a.longitude) -  radians(:lon)) +   sin(radians(:lat)) * sin(radians(a.latitude )))")
    Iterable<Airport> findNearestAirports(@Param("lat") double lat, @Param("lon") double lon);
}