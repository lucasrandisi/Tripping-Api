package Triping.repositories;


import org.springframework.data.repository.CrudRepository;
import Triping.models.User;


public interface UserRepository extends CrudRepository<User, Integer>{
    User findByUsername(String username);
}
