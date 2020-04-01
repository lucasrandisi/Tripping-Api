package Triping.services.specifications;

import Triping.dto.*;
import Triping.models.User;
import Triping.utils.exceptions.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IUserService {

    User findUserByEmail(final String email);

    User findUserByUsername(final String username);

    void saveUser(User user);

    void followUser(String username) throws ResourceNotFoundException;

    UserDto getProfile(String username) throws ResourceNotFoundException;

    Page<UserDto> findFollowedUsers(String username, String searchTerm, Pageable pageRequest);

    Page<UserDto> findUserFollowers(String username, String searchTerm, Pageable pageRequest);
}
