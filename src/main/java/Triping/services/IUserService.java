package Triping.services;

import Triping.controllers.UserDto;
import Triping.models.User;
import Triping.utils.exceptions.HashingException;
import Triping.utils.exceptions.UserAlreadyExistException;

public interface IUserService {

    User registerNewUserAccount(UserDto accountDto) throws UserAlreadyExistException, HashingException;

    boolean validatePassword(String username, String password);

    User findUserByEmail(final String email);
}
