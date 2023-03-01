package es.uniovi.sdi63.sdi2223entrega163.entities;

import org.springframework.context.annotation.Configuration;

@Configuration
public class Associations {

    public static class CreateOffer {
        public static void link(User seller, Offer offer) {
            offer.setSeller( seller );
            seller.getCreatedOffers().add( offer );
        }
    }

    public static class BuyOffer {
        public static void link(User buyer, Offer offer) {
            offer.setBuyer( buyer );
            buyer.getBuyedOffers().add( offer );
        }
    }

    public static class OpenConversation {
        public static void link(User buyer, Offer offer, Conversation conversation) {
            conversation.setOffer( offer );
            conversation.setBuyer( buyer );
            buyer.getConversations().add( conversation );
            offer.getConversations().add( conversation );
        }
    }

    public static class SendMessage {
        public static void link(User sender, Conversation conversation, Message message) {
            message.setConversation( conversation );
            message.setSender( sender );
            sender.getMessages().add( message );
            conversation.getMessages().add( message );
        }
    }

}
