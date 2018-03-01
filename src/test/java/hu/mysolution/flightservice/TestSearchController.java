package hu.mysolution.flightservice;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import hu.mysolution.flightservice.domain.EJBFlightDao;
import hu.mysolution.flightservice.domain.Flight;
import hu.mysolution.flightservice.domain.FlightDao;
import hu.mysolution.flightservice.web.SearchController;

@RunWith(MockitoJUnitRunner.class)
public class TestSearchController {

    @Mock
    FacesContext facesContext;
    
    @Mock
    EntityManager em;

    @Spy
    @InjectMocks
    FlightDao flightDao = new EJBFlightDao();

    @InjectMocks
    SearchController search;

    
    @Test
    public void testSearch() {

        List<Flight> flights = new LinkedList<>();
        flights.add(newFlight("BUD", "FRA", "WIZZ", "WIZZ212", new Date(1000), new Date(1200)));
        flights.add(newFlight("BUD", "BER", "ABL", "ABL323", new Date(1400), new Date(1600)));
        
        mockQuery("select f from Flight f where (:from is null or f.departure >= :from) and (:to is null or f.departure <= :to)", flights);

        search.setDepartureFrom(new Date(1000));
        search.setDepartureTo(new Date(1400));

        search.search();

        assertEquals(search.getFlights(), flights);
    }
    
    
    private void mockQuery(String name, List<Flight> results) {

        Query mockedQuery = mock(Query.class);
        when(mockedQuery.getResultList()).thenReturn(results);
        when(em.createQuery(name)).thenReturn(mockedQuery);
    }

    private Flight newFlight(String from, String to, String carrier, String flightId, Date departure, Date arrival){
        Flight flight =new Flight();
        flight.setAirportFrom(from);
        flight.setAirportTo(to);
        flight.setCarrierName(carrier);
        flight.setFlightId(flightId);
        flight.setArrival(arrival);
        flight.setDeparture(departure);
        return flight;
    }
    
}
