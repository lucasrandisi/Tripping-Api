package Triping.services;

import Triping.controllers.UserDto;
import Triping.models.User;
import Triping.models.VerificationToken;
import Triping.utils.exceptions.HashingException;
import Triping.utils.exceptions.UserAlreadyExistException;

public interface IUserService {

    User registerNewUserAccount(UserDto accountDto) throws UserAlreadyExistException, HashingException;

    boolean validateUserCredentials(String username, String password) throws HashingException;

    User findUserByEmail(final String email);

    VerificationToken getVerificationToken(String token);

    User saveUser(User user);

    void createVerificationToken(User user, String token);
}
