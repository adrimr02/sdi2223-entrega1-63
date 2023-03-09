package es.uniovi.sdi63.sdi2223entrega163.services;

import es.uniovi.sdi63.sdi2223entrega163.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class InsertSampleDataService {

    @Autowired
    private UsersService usersService;

    //@PostConstruct
    public void init() {
        User user1 = new User( "uo284163@uniovi.es", "Adrian", "Martinez" );
        usersService.addUser( user1 );
    }

}
