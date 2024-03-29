package es.uniovi.sdi63.sdi2223entrega163.repositories;

import es.uniovi.sdi63.sdi2223entrega163.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);
    void deleteById(Long Id);

}
