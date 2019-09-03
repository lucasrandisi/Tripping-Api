package Triping.services;

import Triping.controllers.UserDto;
import Triping.models.User;
import Triping.repositories.UserRepository;
import Triping.utils.exceptions.HashingException;
import Triping.utils.exceptions.UserAlreadyExistException;

import org.springframework.beans.factory.annotation.Autowired;

public class UserService implements IUserService{

    @Autowired
    private UserRepository userRepository;

    /* ~~~~~~~~~~ API SERVICES ~~~~~~~~~~~~~ */
    @Override
    public User registerNewUserAccount(final UserDto accountDto) {
        if (this.findUserByEmail(accountDto.getEmail()) != null) {
            throw new UserAlreadyExistException("Ya existe una cuenta registrada con " + accountDto.getEmail());
        }

        //Create new user account deactivated
        final User user = new User();
        user.setUsername(accountDto.getUsername());
        user.setEmail(accountDto.getEmail());
        //try{
            //ToDo: Implementar Hashing Aca
            user.setPassword(accountDto.getPassword());
        /*} catch(HashingException e){
        }*/
        return (userRepository.save(user));
    }

    @Override
    public User findUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }
}
