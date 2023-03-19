package es.uniovi.sdi63.sdi2223entrega163.services;

import es.uniovi.sdi63.sdi2223entrega163.entities.User;
import es.uniovi.sdi63.sdi2223entrega163.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    @Lazy // Evita que salte un error por dependencias circulares
    private OfferService offerService;

    @PostConstruct
    public void init() {
    }

        public List<User> getUsers(){
            List<User> users = new ArrayList<>();
            usersRepository.findAll().forEach(users::add);
            return users;
        }

        public User getUser(long id){
            return usersRepository.findById(id).get();
        }

        public void addUser(User user){
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            usersRepository.save(user);
        }

        public void deleteUser(long id) throws IOException {
            var user = usersRepository.findById( id );
            if (user.isPresent()) {
                for (var offer : user.get().getCreatedOffers())
                    offerService.deleteOffer( offer.getId() );

                usersRepository.deleteById(id);
            }
        }


        public User getUserByEmail(String dni) {
            return usersRepository.findByEmail(dni);
        }

    public boolean authenticate(String email, String password) {
        User user = usersRepository.findByEmail(email);
        if (user == null) {
            return false; // El usuario no existe
        }
        return bCryptPasswordEncoder.matches(password, user.getPassword());
    }

}
