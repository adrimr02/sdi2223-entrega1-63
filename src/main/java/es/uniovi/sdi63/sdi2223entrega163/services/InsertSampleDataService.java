package es.uniovi.sdi63.sdi2223entrega163.services;

import es.uniovi.sdi63.sdi2223entrega163.entities.Offer;
import es.uniovi.sdi63.sdi2223entrega163.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

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
        User[] users = new User[16];
        users[0]= new User("admin@email.com", "Admin", "Admin");
        users[0].setPassword("admin");
        users[0].setRole(rolesService.getRoles()[0]);
        users[1] = new User("user01@email.com", "User01", "01");
        users[1].setPassword("user01");
        users[1].setRole(rolesService.getRoles()[1]);
        users[1].setWallet(100);
        users[2] = new User("user02@email.com", "User02", "02");
        users[2].setPassword("user02");
        users[2].setRole(rolesService.getRoles()[1]);
        users[2].setWallet(200);
        users[3] = new User("user03@email.com", "User03", "03");
        users[3].setPassword("user03");
        users[3].setRole(rolesService.getRoles()[1]);
        users[3].setWallet(300);
        users[4] = new User("user04@email.com", "User04", "04");
        users[4].setPassword("user04");
        users[4].setRole(rolesService.getRoles()[1]);
        users[4].setWallet(50);
        users[5] = new User("user05@email.com", "User05", "05");
        users[5].setPassword("user05");
        users[5].setRole(rolesService.getRoles()[1]);
        users[5].setWallet(15);
        users[6] = new User("user06@email.com", "User06", "06");
        users[6].setPassword("user06");
        users[6].setRole(rolesService.getRoles()[1]);
        users[6].setWallet(500);
        users[7] = new User("user07@email.com", "User07", "07");
        users[7].setPassword("user07");
        users[7].setRole(rolesService.getRoles()[1]);
        users[7].setWallet(0);
        users[8] = new User("user08@email.com", "User08", "08");
        users[8].setPassword("user08");
        users[8].setRole(rolesService.getRoles()[1]);
        users[8].setWallet(10);
        users[9] = new User("user09@email.com", "User09", "09");
        users[9].setPassword("user09");
        users[9].setRole(rolesService.getRoles()[1]);
        users[9].setWallet(100);
        users[10] = new User("user10@email.com", "User10", "10");
        users[10].setPassword("user10");
        users[10].setRole(rolesService.getRoles()[1]);
        users[10].setWallet(70);
        users[11] = new User("user11@email.com", "User11", "11");
        users[11].setPassword("user11");
        users[11].setRole(rolesService.getRoles()[1]);
        users[11].setWallet(1000);
        users[12] = new User("user12@email.com", "User12", "12");
        users[12].setPassword("user12");
        users[12].setRole(rolesService.getRoles()[1]);
        users[12].setWallet(700);
        users[13] = new User("user13@email.com", "User13", "13");
        users[13].setPassword("user13");
        users[13].setRole(rolesService.getRoles()[1]);
        users[13].setWallet(75);
        users[14] = new User("user14@email.com", "User14", "14");
        users[14].setPassword("user14");
        users[14].setRole(rolesService.getRoles()[1]);
        users[14].setWallet(150);
        users[15] = new User("user15@email.com", "User15", "15");
        users[15].setPassword("user15");
        users[15].setRole(rolesService.getRoles()[1]);
        users[15].setWallet(350);

        for(int i=0;i<users.length; i++){
            usersService.addUser(users[i]);
        }

        Offer[] offers = new Offer[13];
        offers[0] = new Offer("Mesa","Mesa grande",30.9, users[1]);
        offers[1] = new Offer("Television","Television 4k de 40 pulgadas",250, users[2]);
        offers[2] = new Offer("Silla","Silla grande",15.5, users[1]);
        offers[3] = new Offer("Sofá","Sofá pequeño",95, users[1]);
        offers[4] = new Offer("Mueble","Mueble pequeño",75.5, users[1]);

        offers[5] = new Offer("Pantalon","Pantalon vaquero gris talla M",15.5, users[4]);
        offers[6] = new Offer("IPhone 11","IPhone 11 256GB",350, users[4]);
        offers[7] = new Offer("Moto","Moto de 200cc",1170, users[5]);
        offers[8] = new Offer("Microondas","Horno microondas \n1 año de antigüedad",110, users[2]);
        offers[9] = new Offer("Monitor PC","Monitor FullHD de 27 pulgadas",115, users[2]);
        offers[10] = new Offer("Portatil","Ordenador portatil con 8GB de ram del año 2019",300, users[6]);
        offers[11] = new Offer("Carrito de bebes","Carrito para bebes nuevo. Incluye adaptador para el coche",120, users[7]);
        offers[12] = new Offer("Silla de oficina","Silla grande de oficina. Muy comoda para estar mucho tiempo",40, users[6]);

        for(int i=0;i<offers.length; i++){
            offerService.addOffer(offers[i]);
        }


        /*var conversation1 = new Conversation(users[2],offers1[0]); //Usuario 2 quiere algo del 1
        var conversation2 = new Conversation(users[3],offers1[0]); //Usuario 3 quiere algo del 1
        var conversation3 = new Conversation(users[1],offers1[1]); //Usuario 1 quiere algo del 2


        conversationService.addConversation(conversation1);
        conversationService.addConversation(conversation2);
        conversationService.addConversation(conversation3);




        var message1 = new Message(users[2],conversation1,"Mensaje de ejemplo");
        messagesService.addMessage(message1);*/

        offerService.buyOffer( offers[1], users[12] );
    }
}
