package hu.mysolution.flightservice.domain;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

@Stateful
// @Alternative
public class EJBFlightDao implements FlightDao {

    @Inject
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public List<Flight> getByDeparture(Date from, Date to) {
        try {
            Query query = entityManager.createQuery(
                    "select f from Flight f where (:from is null or f.departure >= :from) and (:to is null or f.departure <= :to)");
            query.setParameter("from", from);
            query.setParameter("to", to);
            List<Flight> flights = (List<Flight>) query.getResultList();
            return flights;
        } catch (NoResultException e) {
            return null;
        }
    }

    public void persist(Flight entity) {
        if (entity.getId() == null) {
            entityManager.persist(entity);
        } else {
            entityManager.merge(entity);
        }
    }

    public void remove(Flight entity) {
        entityManager.remove(entity);
    }

    public Flight findById(long id) {
        return entityManager.find(Flight.class, id);
    }

    @Override
    public List<Flight> getAll() {
        return getByDeparture(null, null);
    }
}
