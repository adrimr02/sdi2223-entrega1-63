package es.uniovi.sdi63.sdi2223entrega163.services;

import es.uniovi.sdi63.sdi2223entrega163.entities.Conversation;
import es.uniovi.sdi63.sdi2223entrega163.entities.Message;
import es.uniovi.sdi63.sdi2223entrega163.entities.Offer;
import es.uniovi.sdi63.sdi2223entrega163.entities.User;
import es.uniovi.sdi63.sdi2223entrega163.repositories.ConversationRepository;
import es.uniovi.sdi63.sdi2223entrega163.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessagesService {

    @Autowired
    private MessageRepository messageRepository;

    public List<Message> getMessageFromConversation(long conversationId) {
        List<Message> ret = new ArrayList<Message>(messageRepository.findByConversationId(conversationId));
        return ret;
    }

    @Transactional
    public void addMessage(Message message) {
        messageRepository.save(message);
    }
}
