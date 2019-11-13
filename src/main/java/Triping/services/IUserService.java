package Triping.services;

import Triping.dto.AccountDto;
import Triping.dto.InterestDto;
import Triping.dto.UserDto;
import Triping.models.User;
import Triping.models.VerificationToken;
import Triping.utils.exceptions.AlredyAddedException;
import Triping.utils.exceptions.ResourceNotFoundException;
import Triping.utils.exceptions.SameEntityException;
import Triping.utils.exceptions.UserAlreadyExistException;

import java.util.List;
import java.util.Set;

public interface IUserService {

    User registerNewUserAccount(AccountDto accountDto) throws UserAlreadyExistException, ResourceNotFoundException;

    boolean validatePassword(String username, String password);

    User findUserByEmail(final String email);

    User findUserByUsername(final String username);

    VerificationToken getVerificationToken(String token);

    void saveUser(User user);

    void createVerificationToken(User user, String token);

    void followUser(User currentUser, String username) throws ResourceNotFoundException, AlredyAddedException, SameEntityException;

    void unfollowUser(User currentUser, String username) throws ResourceNotFoundException, SameEntityException;

    void addInterest(User currentUser, String id) throws ResourceNotFoundException, AlredyAddedException;

    Set<InterestDto> getInterests(User currentUser);

    void removeInterest(User currentUser, String id) throws ResourceNotFoundException;

    UserDto getProfile(String username) throws ResourceNotFoundException;

    List<UserDto> getFollowed(String username) throws ResourceNotFoundException;

    List<UserDto> getFollowers(String username) throws ResourceNotFoundException;
}
