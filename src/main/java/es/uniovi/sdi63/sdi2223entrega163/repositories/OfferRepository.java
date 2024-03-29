package es.uniovi.sdi63.sdi2223entrega163.repositories;

import es.uniovi.sdi63.sdi2223entrega163.entities.Offer;
import es.uniovi.sdi63.sdi2223entrega163.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OfferRepository extends CrudRepository<Offer, Long> {

    @Query( "select o from Offer o where o.seller = ?1 order by o.date desc" )
    List<Offer> findAllFor( User user );

    @Query( "select o from Offer o order by o.date desc" )
    Page<Offer> findAll(Pageable pageable);

    @Query( "select o from Offer o where lower(o.title) LIKE lower(?1) order by o.date desc" )
    Page<Offer> searchByTitle(Pageable pageable, String query);


    List<Offer> findAllByBuyer( User user );
}
