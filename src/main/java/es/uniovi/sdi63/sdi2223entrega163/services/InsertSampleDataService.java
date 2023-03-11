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

    @Autowired
    private RolesService rolesService;

    @PostConstruct
    public void init() {
        User user1 = new User("user01@email.com", "User01", "01");
        user1.setPassword("user01");
        user1.setRole(rolesService.getRoles()[1]);
        User user2 = new User("user02@email.com", "User02", "02");
        user2.setPassword("user02");
        user2.setRole(rolesService.getRoles()[1]);
        User user3 = new User("user03@email.com", "User03", "03");
        user3.setPassword("user03");
        user3.setRole(rolesService.getRoles()[1]);
        User user4 = new User("user04@email.com", "User04", "04");
        user4.setPassword("user04");
        user4.setRole(rolesService.getRoles()[1]);
        User user5 = new User("user05@rmail.com", "User05", "05");
        user5.setPassword("user05");
        user5.setRole(rolesService.getRoles()[1]);
        User user6 = new User("user06@rmail.com", "User06", "06");
        user6.setPassword("user06");
        user6.setRole(rolesService.getRoles()[1]);
        User user7 = new User("user07@rmail.com", "User07", "07");
        user7.setPassword("user07");
        user7.setRole(rolesService.getRoles()[1]);
        User user8 = new User("user08@rmail.com", "User08", "08");
        user8.setPassword("user08");
        user8.setRole(rolesService.getRoles()[1]);
        User user9 = new User("user09@rmail.com", "User09", "09");
        user9.setPassword("user09");
        user9.setRole(rolesService.getRoles()[1]);
        User user10 = new User("user10@rmail.com", "User10", "10");
        user10.setPassword("user10");
        user10.setRole(rolesService.getRoles()[1]);
        User user11 = new User("user11@rmail.com", "User11", "11");
        user11.setPassword("user11");
        user11.setRole(rolesService.getRoles()[1]);
        User user12 = new User("user12@rmail.com", "User12", "12");
        user12.setPassword("user12");
        user12.setRole(rolesService.getRoles()[1]);
        User user13 = new User("user13@rmail.com", "User13", "13");
        user13.setPassword("user13");
        user13.setRole(rolesService.getRoles()[1]);
        User user14 = new User("user14@rmail.com", "User14", "14");
        user14.setPassword("user14");
        user14.setRole(rolesService.getRoles()[1]);
        User user15 = new User("user15@rmail.com", "User15", "15");
        user15.setPassword("user15");
        user15.setRole(rolesService.getRoles()[1]);
        User user16 = new User("admin@email.com", "Admin", "Admin");
        user16.setPassword("admin");
        user16.setRole(rolesService.getRoles()[0]);
        Set user1Offers = new HashSet<Offer>() {
            {
                add(new Offer("Mesa","Mesa grande",30.9, user1));
                add(new Offer("Silla","Silla grande",15.5, user1));
                add(new Offer("Sofá","Sofá pequeño",95, user1));
                add(new Offer("Mueble","Mueble pequeño",75.5, user1));
            }
        };
        user1.setCreatedOffers(user1Offers);
        /*
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
        usersService.addUser(user7);
        usersService.addUser(user8);
        usersService.addUser(user9);
        usersService.addUser(user10);
        usersService.addUser(user11);
        usersService.addUser(user12);
        usersService.addUser(user13);
        usersService.addUser(user14);
        usersService.addUser(user15);
        usersService.addUser(user16);
    }


}
