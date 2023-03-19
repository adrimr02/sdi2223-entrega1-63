package es.uniovi.sdi63.sdi2223entrega163.repositories;

import es.uniovi.sdi63.sdi2223entrega163.entities.Conversation;
import es.uniovi.sdi63.sdi2223entrega163.entities.Offer;
import es.uniovi.sdi63.sdi2223entrega163.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ConversationRepository extends CrudRepository<Conversation, String> {
    @Query( "select c from Conversation c where c.offer = ?1" )
    List<Conversation> findAllFor(Offer offer );

    @Query( "select c from Conversation c where c.buyer = ?1" )
    List<Conversation> findAsBuyer(User user );

    @Query( "select c from Conversation c where c.id = ?1" )
    Conversation getConversationById(String id);

    @Query( "select c from Conversation c where c.buyer = ?1 and c.offer = ?2")
    Conversation getConversationByPrincipalAndOffer(User user, Offer offer);


}
