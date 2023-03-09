package es.uniovi.sdi63.sdi2223entrega163.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String homeView() {
        return "home";
    }

}
