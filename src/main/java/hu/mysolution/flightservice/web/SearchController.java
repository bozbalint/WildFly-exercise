package hu.mysolution.flightservice.web;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import hu.mysolution.flightservice.domain.Flight;
import hu.mysolution.flightservice.domain.FlightDao;

@Named
@SessionScoped
public class SearchController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private FlightDao flightDao;

    @Inject
    private FacesContext facesContext;

    private Date departureFrom;

    private Date departureTo;

    private List<Flight> flights;

    public void search() {
        flights = flightDao.getByDeparture(departureFrom, departureTo);
    }

    public Date getDepartureFrom() {
        return departureFrom;
    }

    public void setDepartureFrom(Date departureFrom) {
        this.departureFrom = departureFrom;
    }

    public Date getDepartureTo() {
        return departureTo;
    }

    public void setDepartureTo(Date departureTo) {
        this.departureTo = departureTo;
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public String saveAction() {

        // get all existing value but set "editable" to false
        for (Flight flight : flights) {
            if (flight.isEditable()) {
                flight.setEditable(false);
                save(flight);
            }
        }
        // return to current page
        return null;

    }

    public String editAction(Flight flight) {

        flight.setEditable(true);
        return null;
    }

    private void save(Flight flight) {
        try {
            flightDao.persist(flight);
            String message = "A flight updates done.";
            facesContext.addMessage(null, new FacesMessage(message));
        } catch (Exception e) {
            String message = "An error has occured while merge the flight (see log for details)";
            facesContext.addMessage(null, new FacesMessage(message));
        }

    }
}
