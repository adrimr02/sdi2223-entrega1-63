package es.uniovi.sdi63.sdi2223entrega163.controllers;


import es.uniovi.sdi63.sdi2223entrega163.entities.User;
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

    @RequestMapping("/user/list")
    public String getListado(Model model) {
        model.addAttribute("usersList", usersService.getUsers());
        return "user/list";
    }


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        model.addAttribute("user",new User());
        return "login";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signup(@Validated User user, BindingResult result){
        signUpFormValidator.validate(user,result);
        if(result.hasErrors()) {
            return "signup";
        }
        user.setRole(rolesService.getRoles()[1]);
        usersService.addUser(user);
        //securityService.autoLogin(user.getEmail(),user.getPassword());
        return "redirect:home";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

/*
    @RequestMapping(value = { "/home" }, method = RequestMethod.GET)
    public String home(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User activeUser = usersService.getUserByEmail(email);
        model.addAttribute("offerList", activeUser.getCreatedOffers());
        model.addAttribute("usersList", usersService.getUsers());
        return "home";
    }

    @RequestMapping(value = { "/home" }, method = RequestMethod.POST)
    public String deleteUsers(@RequestParam(value = "selectedUsers",
            required = false) List<Long> selectedUsersIds) {
        if(selectedUsersIds != null && !selectedUsersIds.isEmpty()){
            for (long id : selectedUsersIds){
                usersService.deleteUser(id);
            }
        }
        return "user/list";
    }


}
