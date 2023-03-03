package es.uniovi.sdi63.sdi2223entrega163.services;

import es.uniovi.sdi63.sdi2223entrega163.entities.Offer;
import es.uniovi.sdi63.sdi2223entrega163.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Service
public class InsertSampleDataService {

    @Autowired
    private UsersService usersService;

    @PostConstruct
    public void init() {
        User user1 = new User("admin@email.com", "Admin", "Admin");
        user1.setPassword("admin");
        //user1.setRole(rolesService.getRoles()[0]);
        User user2 = new User("user01@email.com", "User01", "01");
        user2.setPassword("user01");
        //user2.setRole(rolesService.getRoles()[1]);
        User user3 = new User("user02@email.com", "User02", "02");
        user3.setPassword("user02");
        //user3.setRole(rolesService.getRoles()[1]);
        User user4 = new User("user03@email.com", "User03", "03");
        user4.setPassword("user03");
        //user4.setRole(rolesService.getRoles()[1]);
        User user5 = new User("user04@email.com", "User04", "04");
        user5.setPassword("user04");
        //user5.setRole(rolesService.getRoles()[1]);
        User user6 = new User("user05@rmail.com", "User05", "05");
        user6.setPassword("user05");
        //user6.setRole(rolesService.getRoles()[1]);
        /*Set user1Offers = new HashSet<Offer>() {
            {
                add(new Offer("Nota A1", 10.0, user1));
                add(new Offer("Nota A2", 9.0, user1));
                add(new Offer("Nota A3", 7.0, user1));
                add(new Offer("Nota A4", 6.5, user1));
            }
        };
        user1.setCreatedOffers(user1Offers);
        Set user2Offers = new HashSet<Offer>() {
            {
                add(new Offer("Nota B1", 5.0, user2));
                add(new Offer("Nota B2", 4.3, user2));
                add(new Offer("Nota B3", 8.0, user2));
                add(new Offer("Nota B4", 3.5, user2));
            }
        };
        user2.setCreatedOffers(user2Offers);
        Set user3Offers = new HashSet<Offer>() {
            {
                ;
                add(new Offer("Nota C1", 5.5, user3));
                add(new Offer("Nota C2", 6.6, user3));
                add(new Offer("Nota C3", 7.0, user3));
            }
        };
        user3.setCreatedOffers(user3Offers);
        Set user4Offers = new HashSet<Offer>() {
            {
                add(new Offer("Nota D1", 10.0, user4))
                add(new Offer("Nota D2", 8.0, user4));
                add(new Offer("Nota D3", 9.0, user4));
            }
        };
        user4.setCreatedOffers(user4Offers);*/
        usersService.addUser(user1);
        usersService.addUser(user2);
        usersService.addUser(user3);
        usersService.addUser(user4);
        usersService.addUser(user5);
        usersService.addUser(user6);
    }


}
