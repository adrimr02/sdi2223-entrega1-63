package es.uniovi.sdi63.sdi2223entrega163.controllers;


import es.uniovi.sdi63.sdi2223entrega163.entities.User;
import es.uniovi.sdi63.sdi2223entrega163.loggers.UserActivityLogger;
import es.uniovi.sdi63.sdi2223entrega163.services.LogService;
import es.uniovi.sdi63.sdi2223entrega163.services.RolesService;
import es.uniovi.sdi63.sdi2223entrega163.services.SecurityService;
import es.uniovi.sdi63.sdi2223entrega163.services.UsersService;
import es.uniovi.sdi63.sdi2223entrega163.validators.SignUpFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Arrays;
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
    private LogService logService;

    @Autowired
    private SignUpFormValidator signUpFormValidator;

    @Autowired
    private UserActivityLogger userActivityLogger;

    @RequestMapping("/user/list")
    public String getListado(Model model, String query) {
        model.addAttribute( "query",
                query != null ? query.strip() : null );
        model.addAttribute( "userList",
                usersService.getUsers() );
        userActivityLogger.log("PET","/user/list", "GET", "");
        return "user/list";
    }

    @RequestMapping(value = "user/list", method = RequestMethod.POST)
    public String deleteUsers(@RequestParam("selectedUsers") List<String> selectedUsers) {
            if(!selectedUsers.isEmpty()) {
                for(String id: selectedUsers){
                    usersService.deleteUser(id);
                }
            }
            userActivityLogger.log("PET","/user/list", "POST", String.valueOf(selectedUsers));
            return "redirect:/user/list";
    }


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signup(@Validated User user, BindingResult result) {
        signUpFormValidator.validate(user, result);
        if (result.hasErrors()) {
            return "signup";
        }
        user.setRole(rolesService.getRoles()[1]);
        usersService.addUser(user);
        securityService.autoLogin(user.getEmail(), user.getPasswordConfirm());
        //userActivityLogger.log("ALTA","/signup", "POST", "");
        return "redirect:login-success";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(Model model) {
        model.addAttribute("user", new User());
        userActivityLogger.log("PET","/signup", "GET", "");
        return "signup";
    }

    @RequestMapping("/login-success")
    public String indexView(Principal principal, HttpSession session) {
        var user = usersService.getUserByEmail( principal.getName() );
        session.setAttribute( "email", user.getEmail() );
        userActivityLogger.log("LOGIN-EX","/login", "POST", user.getEmail());
        userActivityLogger.log("PET","/login-success", "GET", "");
        if (user.getRole().equals( rolesService.getRoles()[0] )) {
            return "redirect:/user/list";

        } else {
            session.setAttribute( "wallet", user.getWallet() );
            return "redirect:/offer/my-offers";
        }
    }

    @RequestMapping("/user/logs")
    public String getListadoLogs(Model model, String query) {
        model.addAttribute( "query",
                query != null ? query.strip() : null );
        model.addAttribute( "logsList",
                logService.getLogs() );
        userActivityLogger.log("PET","/user/logs", "GET", "");
        return "user/logs";
    }

    @RequestMapping(value = "user/logs", method = RequestMethod.POST)
    public String deleteLogs() {
        logService.deleteUsers();
        userActivityLogger.log("PET","/user/logs", "POST", "");
        return "redirect:/user/logs";
    }
}
