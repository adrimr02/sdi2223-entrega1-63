package es.uniovi.sdi63.sdi2223entrega163.controllers;


import es.uniovi.sdi63.sdi2223entrega163.entities.Log;
import es.uniovi.sdi63.sdi2223entrega163.entities.Log.LogTypes;
import es.uniovi.sdi63.sdi2223entrega163.entities.User;
import es.uniovi.sdi63.sdi2223entrega163.loggers.UserActivityLogger;
import es.uniovi.sdi63.sdi2223entrega163.services.LogService;
import es.uniovi.sdi63.sdi2223entrega163.services.RolesService;
import es.uniovi.sdi63.sdi2223entrega163.services.SecurityService;
import es.uniovi.sdi63.sdi2223entrega163.services.UsersService;
import es.uniovi.sdi63.sdi2223entrega163.util.ParamFormatter;
import es.uniovi.sdi63.sdi2223entrega163.util.Round;
import es.uniovi.sdi63.sdi2223entrega163.validators.SignUpFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.http.HttpRequest;
import java.security.Principal;
import java.util.List;

@Controller
public class UsersController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private RolesService rolesService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private SignUpFormValidator signUpFormValidator;

    @Autowired
    private UserActivityLogger logger;
    @RequestMapping("/user/list")
    public String getListado(Model model, String query) {
        model.addAttribute( "query",
                query != null ? query.strip() : null );
        model.addAttribute( "userList",
                usersService.getUsers() );
        return "user/list";
    }

    @RequestMapping(value = "user/list", method = RequestMethod.POST)
    public String deleteUsers(@RequestParam("selectedUsers") List<Long> selectedUsers) throws IOException {
            if(!selectedUsers.isEmpty()) {
                for(long id: selectedUsers){
                    usersService.deleteUser(id);
                }
            }
            return "redirect:/user/list";
    }


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signup(@Validated User user, BindingResult result, HttpServletRequest request) {
        signUpFormValidator.validate(user, result);
        if (result.hasErrors()) {
            return "signup";
        }
        user.setRole(rolesService.getRoles()[1]);
        usersService.addUser(user);
        securityService.autoLogin(user.getEmail(), user.getPasswordConfirm());
        logger.log( LogTypes.ALTA, "/signup", "POST",
                ParamFormatter.format( request.getParameterMap() ) );
        return "redirect:login-success";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @RequestMapping("/login-success")
    public String indexView(Principal principal, HttpSession session) {
        var user = usersService.getUserByEmail( principal.getName() );
        session.setAttribute( "email", user.getEmail() );
        if (user.getRole().equals( rolesService.getRoles()[0] )) {
            return "redirect:/user/list";

        } else {
            session.setAttribute( "wallet", Round.twoCents( user.getWallet() ));
            return "redirect:/offer/my-offers";
        }
    }

}
