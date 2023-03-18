package es.uniovi.sdi63.sdi2223entrega163.services;

import es.uniovi.sdi63.sdi2223entrega163.entities.Conversation;
import es.uniovi.sdi63.sdi2223entrega163.entities.Message;
import es.uniovi.sdi63.sdi2223entrega163.entities.Offer;
import es.uniovi.sdi63.sdi2223entrega163.entities.User;
import es.uniovi.sdi63.sdi2223entrega163.repositories.ConversationRepository;
import es.uniovi.sdi63.sdi2223entrega163.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessagesService {

    @Autowired
    private MessageRepository messageRepository;

    public List<Message> getMessageFromConversation(String conversationId) {
        List<Message> ret = new ArrayList<Message>(messageRepository.findByConversationId(conversationId));
        return ret;
    }

    public void addMessage(Message message) {
        messageRepository.save(message);
    }
}
