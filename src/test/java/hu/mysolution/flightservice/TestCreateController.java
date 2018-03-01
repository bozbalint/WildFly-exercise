package hu.mysolution.flightservice;

import static org.mockito.Mockito.verify;

import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import hu.mysolution.flightservice.domain.EJBFlightDao;
import hu.mysolution.flightservice.domain.Flight;
import hu.mysolution.flightservice.domain.FlightDao;
import hu.mysolution.flightservice.web.CreateController;

@RunWith(MockitoJUnitRunner.class)
public class TestCreateController {

    @Mock
    FacesContext facesContext;
    
    @Mock
    EntityManager em;

    @Spy
    @InjectMocks
    FlightDao flightDao = new EJBFlightDao();

    @Spy
    Flight flight = new Flight();

    @InjectMocks
    CreateController create;

    
    @Test
    public void testCreate() {

        create.create();
        
        verify(em).persist(flight);
    }

}
