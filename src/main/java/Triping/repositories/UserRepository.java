package Triping.repositories;


import Triping.dto.UserDto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import Triping.models.User;


public interface UserRepository extends JpaRepository<User, Long>{
    User findByUsername(String username);

    User findByEmail(String email);

    @Override
    User save(User user);

    @Override
    void delete(User user);

    @Query("select f from User u join u.following f where u.username=?1 and f.username like %?2%")
    Page<User> findFollowedUsers(String username, String searchTerm, Pageable pageRequest);

    @Query("select f from User u join u.followers f where u.username=?1 and f.username like %?2%")
    Page<User> findUserFollowers(String username, String searchTerm, Pageable pageRequest);
}
