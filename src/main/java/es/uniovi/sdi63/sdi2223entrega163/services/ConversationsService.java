package es.uniovi.sdi63.sdi2223entrega163.services;

import es.uniovi.sdi63.sdi2223entrega163.entities.Conversation;
import es.uniovi.sdi63.sdi2223entrega163.entities.Offer;
import es.uniovi.sdi63.sdi2223entrega163.entities.User;
import es.uniovi.sdi63.sdi2223entrega163.repositories.ConversationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConversationsService {

    @Autowired
    private OfferService offerService;

    @Autowired
    private ConversationRepository conversationRepository;

    public List<Conversation> getAllConversationsFrom(User user) {
        List<Conversation> ret = new ArrayList<Conversation>();
        List<Offer> offers = offerService.getAllOffersFrom(user);
        for(Offer offer : offers ){
            ret.addAll(conversationRepository.findAllFor(offer));
        }
        List<Conversation> conversationsAsBuyer = new ArrayList<Conversation>(conversationRepository.findAsBuyer(user));
        ret.addAll(conversationsAsBuyer);
        return ret;
    }

    public Conversation getConversationByPrincipalandOffer(User user, Offer offer){
        return conversationRepository.getConversationByPrincipalAndOffer(user,offer);
    }

    public Conversation getConversationById(String id){
        return conversationRepository.getConversationById(id);
    }



    public void addConversation(Conversation conversation) {
        conversationRepository.save(conversation);
    }

}
