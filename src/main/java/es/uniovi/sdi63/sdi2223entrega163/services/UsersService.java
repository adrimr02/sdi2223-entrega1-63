package es.uniovi.sdi63.sdi2223entrega163.services;

import es.uniovi.sdi63.sdi2223entrega163.entities.User;
import es.uniovi.sdi63.sdi2223entrega163.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostConstruct
    public void init() {
    }

        public List<User> getUsers(){
            List<User> users = new ArrayList<User>();
            usersRepository.findAll().forEach(users::add);
            return users;
        }

        public User getUser(Long id){
            return usersRepository.findById(id).get();
        }

        public void addUser(User user){
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            usersRepository.save(user);
        }

        public void deleteUser(Long id){
            usersRepository.deleteById(id);
        }

        public void deleteUsers(List<Long> users){
            for (Long id: users){
                deleteUser(id);
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
