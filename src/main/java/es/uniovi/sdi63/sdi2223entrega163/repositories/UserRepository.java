package es.uniovi.sdi63.sdi2223entrega163.repositories;

import es.uniovi.sdi63.sdi2223entrega163.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {
}
