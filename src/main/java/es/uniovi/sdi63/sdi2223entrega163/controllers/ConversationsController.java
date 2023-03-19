package es.uniovi.sdi63.sdi2223entrega163.controllers;

import es.uniovi.sdi63.sdi2223entrega163.services.ConversationsService;
import es.uniovi.sdi63.sdi2223entrega163.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class ConversationsController {

    @Autowired
    private ConversationsService conversationService;
    @Autowired
    private UsersService usersService;

    @RequestMapping("/conversations/list")
    public String userConversationsView(Model model, Principal principal) {
        String email = principal.getName();
        var user = usersService.getUserByEmail( email );
        model.addAttribute( "conversationList",
                conversationService.getAllConversationsFrom( user ) );
        return "/conversations/list";
    }


}
