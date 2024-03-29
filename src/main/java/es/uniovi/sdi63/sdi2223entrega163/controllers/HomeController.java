package es.uniovi.sdi63.sdi2223entrega163.controllers;

import es.uniovi.sdi63.sdi2223entrega163.entities.Log.LogTypes;
import es.uniovi.sdi63.sdi2223entrega163.loggers.UserActivityLogger;
import es.uniovi.sdi63.sdi2223entrega163.services.OfferService;
import es.uniovi.sdi63.sdi2223entrega163.services.RolesService;
import es.uniovi.sdi63.sdi2223entrega163.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class HomeController {

    @Autowired
    private OfferService offerService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private RolesService rolesService;

    @Autowired
    private UserActivityLogger userActivityLogger;

    @RequestMapping("/")
    public String indexView(Principal principal) {
        if (principal != null) return "redirect:/home";

        return "index";
    }

    @RequestMapping("/home")
    public String homeView(Model model, Principal principal, Pageable pageable,
                           @RequestParam(name = "search", required = false)
                           String query) {
        var user = usersService.getUserByEmail( principal.getName() );
        if (user.getRole().equals( rolesService.getRoles()[0] )) {
            model.addAttribute( "query",
                    query != null ? query.strip() : null );
            model.addAttribute( "userList",
                    usersService.getUsers() );
        } else if (user.getRole().equals( rolesService.getRoles()[1] )) {
            var offers = offerService.getAllOffers( pageable, query );
            model.addAttribute( "query",
                    query != null ? query.strip() : null );
            model.addAttribute( "page", offers);
            model.addAttribute( "offerList", offers.getContent() );
        }
        //userActivityLogger.log( LogTypes.PET,"/home", "GET", "");
        return "home";
    }

}
