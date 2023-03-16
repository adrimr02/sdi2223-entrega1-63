package es.uniovi.sdi63.sdi2223entrega163.services;

import es.uniovi.sdi63.sdi2223entrega163.entities.Conversation;
import es.uniovi.sdi63.sdi2223entrega163.entities.Offer;
import es.uniovi.sdi63.sdi2223entrega163.entities.User;
import es.uniovi.sdi63.sdi2223entrega163.repositories.ConversationRepository;
import es.uniovi.sdi63.sdi2223entrega163.repositories.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ConversationsService {
    @Autowired
    private UsersService usersService;

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
        return ret;
    }

    public List<Conversation> getAllConversationsFrom(Offer offer) {
        List<Conversation> ret = new ArrayList<Conversation>();
        List<Offer> offers = offerService.getAllOffersFrom(user);

        return ret;
    }

    public void addConversation(Conversation conversation) {
        conversationRepository.save( conversation );
    }

}
