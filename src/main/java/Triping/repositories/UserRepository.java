package Triping.repositories;


import org.springframework.data.repository.CrudRepository;

import Triping.models.User;


public interface UserRepository extends CrudRepository<User, Long>{
    User findByUsername(String username);

    User findByEmail(String email);

    @Override
    User save(User user);

    @Override
    void delete(User user);
}
