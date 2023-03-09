package es.uniovi.sdi63.sdi2223entrega163.services;

import es.uniovi.sdi63.sdi2223entrega163.entities.User;
import es.uniovi.sdi63.sdi2223entrega163.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class UsersService {

    @Autowired
    private UserRepository userRepository;

    public void addUser(User user) {
        userRepository.save( user );
    }

    public User findFirst() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach( users::add );
        return users.stream().findFirst().get();
    }

}
