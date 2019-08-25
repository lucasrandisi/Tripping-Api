package Triping.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import Triping.models.User;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Integer>{
    User findByUsername(String username);
}
