package hu.mysolution.flightservice.domain;

import java.util.Date;
import java.util.List;

public interface FlightDao {
    List<Flight> getByDeparture(Date from, Date to);

    void persist(Flight flight);

    void remove(Flight flight);

    Flight findById(long id);

    List<Flight> getAll();

}
