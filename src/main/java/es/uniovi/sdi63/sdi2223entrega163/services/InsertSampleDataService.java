package es.uniovi.sdi63.sdi2223entrega163.services;

import antlr.debug.MessageAdapter;
import es.uniovi.sdi63.sdi2223entrega163.entities.Conversation;
import es.uniovi.sdi63.sdi2223entrega163.entities.Message;
import es.uniovi.sdi63.sdi2223entrega163.entities.Offer;
import es.uniovi.sdi63.sdi2223entrega163.entities.User;
import es.uniovi.sdi63.sdi2223entrega163.repositories.MessageRepository;
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
    private OfferService offerService;

    @Autowired
    private RolesService rolesService;

    @Autowired
    private ConversationsService conversationService;

    @Autowired
    private MessagesService messagesService;

    @PostConstruct
    public void init() {
        User user1 = new User("user01@email.com", "User01", "01");
        user1.setPassword("user01");
        user1.setRole(rolesService.getRoles()[1]);
        user1.setWallet(100);
        User user2 = new User("user02@email.com", "User02", "02");
        user2.setPassword("user02");
        user2.setRole(rolesService.getRoles()[1]);
        user2.setWallet(200);
        User user3 = new User("user03@email.com", "User03", "03");
        user3.setPassword("user03");
        user3.setRole(rolesService.getRoles()[1]);
        user3.setWallet(300);
        User user4 = new User("user04@email.com", "User04", "04");
        user4.setPassword("user04");
        user4.setRole(rolesService.getRoles()[1]);
        user4.setWallet(50);
        User user5 = new User("user05@email.com", "User05", "05");
        user5.setPassword("user05");
        user5.setRole(rolesService.getRoles()[1]);
        user5.setWallet(15);
        User user6 = new User("user06@email.com", "User06", "06");
        user6.setPassword("user06");
        user6.setRole(rolesService.getRoles()[1]);
        user6.setWallet(500);
        User user7 = new User("user07@email.com", "User07", "07");
        user7.setPassword("user07");
        user7.setRole(rolesService.getRoles()[1]);
        user7.setWallet(0);
        User user8 = new User("user08@email.com", "User08", "08");
        user8.setPassword("user08");
        user8.setRole(rolesService.getRoles()[1]);
        user8.setWallet(10);
        User user9 = new User("user09@email.com", "User09", "09");
        user9.setPassword("user09");
        user9.setRole(rolesService.getRoles()[1]);
        user9.setWallet(100);
        User user10 = new User("user10@email.com", "User10", "10");
        user10.setPassword("user10");
        user10.setRole(rolesService.getRoles()[1]);
        user10.setWallet(70);
        User user11 = new User("user11@email.com", "User11", "11");
        user11.setPassword("user11");
        user11.setRole(rolesService.getRoles()[1]);
        user11.setWallet(1000);
        User user12 = new User("user12@email.com", "User12", "12");
        user12.setPassword("user12");
        user12.setRole(rolesService.getRoles()[1]);
        user12.setWallet(700);
        User user13 = new User("user13@email.com", "User13", "13");
        user13.setPassword("user13");
        user13.setRole(rolesService.getRoles()[1]);
        user13.setWallet(75);
        User user14 = new User("user14@email.com", "User14", "14");
        user14.setPassword("user14");
        user14.setRole(rolesService.getRoles()[1]);
        user14.setWallet(150);
        User user15 = new User("user15@email.com", "User15", "15");
        user15.setPassword("user15");
        user15.setRole(rolesService.getRoles()[1]);
        user15.setWallet(350);
        User user16 = new User("admin@email.com", "Admin", "Admin");
        user16.setPassword("admin");
        user16.setRole(rolesService.getRoles()[0]);

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

        var oferta1 = new Offer("Mesa","Mesa grande",30.9, user1);
        var oferta2 = new Offer("Television","Television 4k de 40 pulgadas",250, user2);
        var oferta3 = new Offer("Silla","Silla grande",15.5, user1);
        var oferta4 = new Offer("Sofá","Sofá pequeño",95, user1);
        var oferta5 = new Offer("Mueble","Mueble pequeño",75.5, user1);

        offerService.addOffer( oferta1 );
        offerService.addOffer( oferta2 );
        offerService.addOffer( oferta3 );
        offerService.addOffer( oferta4 );
        offerService.addOffer( oferta5 );


        offerService.buyOffer( oferta2, user12 );

        var conversation1 = new Conversation(user2,oferta1); //Usuario 2 quiere algo del 1
        conversation1.setMessages(new HashSet<Message>());
        var conversation2 = new Conversation(user2,oferta3); //Usuario 2 quiere algo del 1
        var conversation3 = new Conversation(user3,oferta1); //Usuario 3 quiere algo del 1
        var conversation4 = new Conversation(user1,oferta2); //Usuario 1 quiere algo del 2


        conversationService.addConversation(conversation1);
        conversationService.addConversation(conversation2);
        conversationService.addConversation(conversation3);
        conversationService.addConversation(conversation4);



        var message1 = new Message(user2,conversation1,"Mensaje de ejemplo");
        messagesService.addMessage(message1);

    }
}
