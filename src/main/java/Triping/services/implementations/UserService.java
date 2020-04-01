package Triping.services.implementations;

import Triping.dto.*;
import Triping.models.Group;
import Triping.models.User;
import Triping.repositories.GroupRepository;
import Triping.repositories.UserRepository;
import Triping.services.specifications.IAccountService;
import Triping.services.specifications.IUserService;
import Triping.utils.exceptions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private IAccountService accountService;

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User findUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void followUser(String username) throws ResourceNotFoundException {
        final User user = findUserByUsername(username);
        if (user == null) { throw new ResourceNotFoundException("Usuario no encontrado"); }

        final User authenticatedUser = userRepository.findByUsername(accountService.currentAuthenticatedUser());
        if (authenticatedUser.doesFollow(user)) {
            authenticatedUser.unFollow(user);
        } else {
            authenticatedUser.follow(user);
        }
    }

    @Override
    public UserDto getProfile(String username) throws ResourceNotFoundException {
        User user = findUserByUsername(username);

        if (user == null) {
            throw new ResourceNotFoundException("Usuario no encontrado");
        }
        return new UserDto(user);
    }

    @Override
    public Page<UserDto> findFollowedUsers(String username, String searchTerm, Pageable pageRequest) {
        final Page<User> following = userRepository.findFollowedUsers(username, searchTerm, pageRequest);
        final User authenticatedUser = userRepository.findByUsername(accountService.currentAuthenticatedUser());
        return following.map(p -> new UserDto(p, authenticatedUser));
    }

    @Override
    public Page<UserDto> findUserFollowers(String username, String searchTerm, Pageable pageRequest) {
        final Page<User> followers = userRepository.findUserFollowers(username, searchTerm, pageRequest);
        final User authenticatedUser = userRepository.findByUsername(accountService.currentAuthenticatedUser());
        return followers.map(p -> new UserDto(p, authenticatedUser));
    }
}
