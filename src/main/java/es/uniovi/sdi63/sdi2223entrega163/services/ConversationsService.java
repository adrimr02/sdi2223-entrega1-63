package es.uniovi.sdi63.sdi2223entrega163.services;

import es.uniovi.sdi63.sdi2223entrega163.entities.Conversation;
import es.uniovi.sdi63.sdi2223entrega163.entities.Message;
import es.uniovi.sdi63.sdi2223entrega163.entities.Offer;
import es.uniovi.sdi63.sdi2223entrega163.entities.User;
import es.uniovi.sdi63.sdi2223entrega163.repositories.ConversationRepository;
import es.uniovi.sdi63.sdi2223entrega163.repositories.MessageRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Service
public class ConversationsService {

    @Autowired
    private OfferService offerService;

    @Autowired
    private MessagesService messagesService;

    @Autowired
    private ConversationRepository conversationRepository;

    public List<Conversation> getAllConversationsFrom(User user) {
        List<Conversation> ret = new ArrayList<>();
        List<Offer> offers = offerService.getAllOffersFrom(user);
        for(Offer offer : offers ){
            ret.addAll(conversationRepository.findAllFor(offer));
        }
        List<Conversation> conversationsAsBuyer = new ArrayList<>(conversationRepository.findAsBuyer(user));
        ret.addAll(conversationsAsBuyer);
        return ret;
    }

    public Conversation getConversationByPrincipalandOffer(User user, Offer offer){
        return conversationRepository.getConversationByPrincipalAndOffer(user,offer);
    }

    public Conversation getConversationById(Long id){
        return conversationRepository.getConversationById(id);
    }



    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public long createConversationWithMessage(User user, Offer offer, String message) {
        var newConversation = new Conversation( user, offer );

        conversationRepository.save(newConversation);
        messagesService.addMessage( new Message( user, newConversation, message ) );

        return newConversation.getId();
    }

}
