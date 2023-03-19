package es.uniovi.sdi63.sdi2223entrega163.repositories;

import es.uniovi.sdi63.sdi2223entrega163.entities.Message;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Long> {

    @Query( "select m from Message m where m.conversation.id = ?1" )
    List<Message> findByConversationId(Long id);
}
