package es.uniovi.sdi63.sdi2223entrega163.controllers;


import es.uniovi.sdi63.sdi2223entrega163.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UsersController {

    @Autowired
    private UsersService usersService;



    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }
}
