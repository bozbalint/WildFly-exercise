package hu.mysolution.flightservice.web;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import hu.mysolution.flightservice.domain.Flight;
import hu.mysolution.flightservice.domain.FlightDao;

@Named
@RequestScoped
public class CreateController {

    @Inject
    private FacesContext facesContext;

    @Inject
    private FlightDao flightDao;

    @Named
    @Produces
    @RequestScoped
    private Flight newFlight = new Flight();

    public void create() {
        try {
            flightDao.persist(newFlight);
            String message = "A new flight with id " + newFlight.getId() + " has been created successfully";
            facesContext.addMessage(null, new FacesMessage(message));
        } catch (Exception e) {
            String message = "An error has occured while creating the flight (see log for details)";
            facesContext.addMessage(null, new FacesMessage(message));
        }
    }
}
