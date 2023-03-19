package es.uniovi.sdi63.sdi2223entrega163.controllers;

import es.uniovi.sdi63.sdi2223entrega163.entities.Conversation;
import es.uniovi.sdi63.sdi2223entrega163.entities.Message;
import es.uniovi.sdi63.sdi2223entrega163.entities.Offer;
import es.uniovi.sdi63.sdi2223entrega163.entities.User;
import es.uniovi.sdi63.sdi2223entrega163.services.ConversationsService;
import es.uniovi.sdi63.sdi2223entrega163.services.MessagesService;
import es.uniovi.sdi63.sdi2223entrega163.services.OfferService;
import es.uniovi.sdi63.sdi2223entrega163.services.UsersService;
import es.uniovi.sdi63.sdi2223entrega163.validators.OfferFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;

@Controller
public class MessageController {

    @Autowired
    private MessagesService messagesService;
    @Autowired
    private UsersService usersService;

    @Autowired
    private ConversationsService conversationsService;

    @RequestMapping("/conversations/{id}")
    public String userMessageView(Model model, @PathVariable String id) {
        model.addAttribute( "messageList",
                messagesService.getMessageFromConversation(id) );
        model.addAttribute( "id",id);
        return "/conversations/messageList";
    }

    @RequestMapping("/conversations/{id}/new")
    public String newMessageView(Model model, @PathVariable String id) {
        model.addAttribute("message", new Message());
        model.addAttribute( "id",id);
        return "/conversations/newMessage";
    }

    @RequestMapping(value = "/conversations/{id}/new", method = RequestMethod.POST)
    public String newMessageForm(@ModelAttribute Message message, Principal principal, Model model,  @PathVariable String id, Offer offer) {
        String email = principal.getName();
        var user = usersService.getUserByEmail(email);
        var conversation = conversationsService.getConversationById(id);

        message.setSender(user);
        if(conversation != null) {
            message.setConversation(conversation);
        }else{
            //message.setConversation(new Conversation(user,)); Oferta en la que se clickea
        }
        message.setTime(LocalDateTime.now());
        model.addAttribute( "message", message);
        model.addAttribute( "id",id);
        messagesService.addMessage(message);
        return "redirect:/conversations/" + id;
    }

}
