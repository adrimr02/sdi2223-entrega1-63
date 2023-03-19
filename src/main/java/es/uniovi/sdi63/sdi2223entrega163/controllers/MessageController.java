package es.uniovi.sdi63.sdi2223entrega163.controllers;

import es.uniovi.sdi63.sdi2223entrega163.entities.Message;
import es.uniovi.sdi63.sdi2223entrega163.services.ConversationsService;
import es.uniovi.sdi63.sdi2223entrega163.services.MessagesService;
import es.uniovi.sdi63.sdi2223entrega163.services.OfferService;
import es.uniovi.sdi63.sdi2223entrega163.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private OfferService offerService;

    @RequestMapping("/conversations/{id}")
    public String userMessageView(Model model, @PathVariable long id) {
        model.addAttribute( "messageList",
                messagesService.getMessageFromConversation(id) );
        model.addAttribute( "id",id);
        return "/conversations/messageList";
    }

    @RequestMapping("/conversations/{id}/new")
    public String newMessageView(Model model, @PathVariable String id) {
        model.addAttribute("message", new Message());
        model.addAttribute( "id",id);
        model.addAttribute( "newConversation",false);
        return "/conversations/newMessage";
    }

    @RequestMapping(value = "/conversations/{id}/new", method = RequestMethod.POST)
    public String newMessageForm(@ModelAttribute Message message,Principal principal, Model model,
                                 @PathVariable long id) {
        String email = principal.getName();
        var user = usersService.getUserByEmail(email);
        var conversation = conversationsService.getConversationById(id);
        message.setSender(user);
        message.setConversation(conversation);
        message.setTime(LocalDateTime.now());
        model.addAttribute( "message", message);
        model.addAttribute( "id",id);
        messagesService.addMessage(message);
        return "redirect:/conversations/" + id;
    }


    @RequestMapping("/{id}/message/new")
    public String newMessageWithoutConversationForm(Model model, @PathVariable String id) {
        model.addAttribute( "id",id);
        model.addAttribute( "newConversation",true);
        return "/conversations/newMessage";
    }

    @RequestMapping(value = "/{id}/message/new", method = RequestMethod.POST)
    public String newMessageWithoutConversationForm(@RequestParam String message, Principal principal, Model model,
                                 @PathVariable long id) {
        String email = principal.getName();
        var user = usersService.getUserByEmail(email);
        var offer = offerService.getOfferById(id);
        var conversation = conversationsService.getConversationByPrincipalandOffer(user,offer);

        if (message.isBlank()) {
            model.addAttribute( "id",id);
            model.addAttribute( "newConversation",true);
            return "/conversations/newMessage";
        }

        if(offer.getSeller().equals(user)) {
            return "redirect:/home";
        }

        if(conversation != null) {
            return "redirect:/conversations/"+ conversation.getId() +"/new";
        }

        long cId = conversationsService.createConversationWithMessage( user, offer, message );

        return "redirect:/conversations/" + cId;
    }



}
