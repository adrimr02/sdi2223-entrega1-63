package es.uniovi.sdi63.sdi2223entrega163.controllers;

import es.uniovi.sdi63.sdi2223entrega163.entities.Message;
import es.uniovi.sdi63.sdi2223entrega163.services.ConversationsService;
import es.uniovi.sdi63.sdi2223entrega163.services.MessagesService;
import es.uniovi.sdi63.sdi2223entrega163.services.OfferService;
import es.uniovi.sdi63.sdi2223entrega163.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.nio.file.AccessDeniedException;
import java.security.Principal;

@Controller
public class ConversationsController {

    @Autowired
    private MessagesService messagesService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private ConversationsService conversationsService;

    @Autowired
    private OfferService offerService;

    @RequestMapping("/conversations/list")
    public String userConversationsView(Model model, Principal principal) {
        String email = principal.getName();
        var user = usersService.getUserByEmail( email );
        model.addAttribute( "conversationList",
                conversationsService.getAllConversationsFrom( user ) );
        return "/conversations/list";
    }

    @RequestMapping("/conversations/{id}")
    public String userMessageView(Model model, @PathVariable long id, Principal principal) throws AccessDeniedException {

        String email = principal.getName();
        var user = usersService.getUserByEmail(email);
        var conversation = conversationsService.getConversationById(id);

        if(!user.equals(conversation.getBuyer()) && !user.equals(conversation.getOffer().getSeller())){
            throw new AccessDeniedException("Forbidden Access");
        }

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
    public String newMessageForm(@RequestParam String message, Principal principal, Model model,
                                 @PathVariable long id) throws AccessDeniedException {
        String email = principal.getName();
        var user = usersService.getUserByEmail(email);
        var conversation = conversationsService.getConversationById(id);

        if(!user.equals(conversation.getBuyer()) && !user.equals(conversation.getOffer().getSeller())){
            throw new AccessDeniedException("Forbidden Access");
        }

        if (message.isBlank()) {
            model.addAttribute( "id",id);
            model.addAttribute( "newConversation",false);
            return "/conversations/newMessage";
        }
        messagesService.addMessage(new Message( user, conversation, message ));
        return "redirect:/conversations/" + id;
    }


    @RequestMapping("/{id}/message/new")
    public String newMessageWithoutConversationForm(Model model, @PathVariable long id) {
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
            messagesService.addMessage(new Message(user,conversation,message));
            return "redirect:/conversations/"+ conversation.getId();
        }

        long cId = conversationsService.createConversationWithMessage( user, offer, message );

        return "redirect:/conversations/" + cId;
    }


}
