package Triping.services;

import Triping.dto.AccountDto;
import Triping.models.User;
import Triping.models.VerificationToken;
import Triping.repositories.UserRepository;
import Triping.repositories.VerificationTokenRepository;
import Triping.utils.exceptions.ResourceNotFoundException;
import Triping.utils.exceptions.UserAlreadyExistException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService implements IUserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationTokenRepository tokenRepository;


    /* ~~~~~~~~~~ API SERVICES ~~~~~~~~~~~~~ */
    @Override
    public User registerNewUserAccount(final AccountDto accountDto) {
        if (this.findUserByEmail(accountDto.getEmail()) != null) {
            throw new UserAlreadyExistException("Ya existe una cuenta registrada con " + accountDto.getEmail());
        }

        if (this.findUserByUsername(accountDto.getUsername()) != null) {
            throw new UserAlreadyExistException("Ya existe una cuenta registrada con nombre de usuario " + accountDto.getUsername());
        }

        //Create new user account deactivated
        final User user = new User();
        user.setUsername(accountDto.getUsername());
        user.setEmail(accountDto.getEmail());

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(accountDto.getPassword());

        user.setPassword(hashedPassword);

        return (userRepository.save(user));
    }


    public boolean validatePassword(String username, String password){
        User user = userRepository.findByUsername(username);
        if (user == null){
            return false;
        }

        String hashedPassword = user.getPassword();

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(password, hashedPassword);
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
    public VerificationToken getVerificationToken(String verificationToken) {
        return tokenRepository.findByToken(verificationToken);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void createVerificationToken(final User user, final String token) {
        VerificationToken verificationToken = new VerificationToken(user, token);
        tokenRepository.save(verificationToken);
    }

    @Override
    public void followUser(User currentUser, String username) throws ResourceNotFoundException {
        User followedUser = userRepository.findByUsername(username);
        if(followedUser != null){
            if(!currentUser.getFriends().contains(followedUser) && currentUser != followedUser){
                currentUser.getFriends().add(followedUser);
                userRepository.save(currentUser);
            }else {
                throw new ResourceNotFoundException();
            }

        }
        else{
            throw new ResourceNotFoundException();
        }
    }

    @Override
    public void unfollowUser(User currentUser, String username) throws ResourceNotFoundException {
        User toUnfollowUser = userRepository.findByUsername(username);
        if(toUnfollowUser != null){
            if(currentUser.getFriends().contains(toUnfollowUser) && currentUser != toUnfollowUser){
                currentUser.getFriends().remove(toUnfollowUser);
                userRepository.save(currentUser);
            }else{
                throw new ResourceNotFoundException();
            }

        }else{
            throw new ResourceNotFoundException();
        }
    }
}
