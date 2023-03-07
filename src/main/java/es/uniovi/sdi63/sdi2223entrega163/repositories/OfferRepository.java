package es.uniovi.sdi63.sdi2223entrega163.repositories;

import es.uniovi.sdi63.sdi2223entrega163.entities.Offer;
import es.uniovi.sdi63.sdi2223entrega163.entities.Offer.OfferState;
import es.uniovi.sdi63.sdi2223entrega163.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OfferRepository extends CrudRepository<Offer, String> {
    @Query("select o from Offer o where o.state = ?1 order by o.date DESC")
    List<Offer> findAllAvailable(OfferState state);

    @Query( "select o from Offer o where o.seller = ?1 order by o.date desc" )
    List<Offer> findAllFor( User user );

    //@Query( "select o from Offer o where o.state = es.uniovi.sdi63.sdi2223entrega163.entities.Offer.OfferState.AVAILABLE order by o.date desc" )
    //List<Offer> findAllAvailable();

}
