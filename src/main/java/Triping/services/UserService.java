package Triping.services;

import Triping.controllers.UserDto;
import Triping.models.User;
import Triping.models.VerificationToken;
import Triping.repositories.UserRepository;
import Triping.repositories.VerificationTokenRepository;
import Triping.utils.Hashing;
import Triping.utils.exceptions.HashingException;
import Triping.utils.exceptions.UserAlreadyExistException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserService implements IUserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationTokenRepository tokenRepository;

    private final int SALT_LENGTH = 4;

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

        //Password Hashing
        try{
            Optional<String> optSalt = Hashing.generateSalt(SALT_LENGTH);
            String salt = optSalt.orElseThrow(HashingException::new);

            Optional<String> optHashedPassword = Hashing.hashPassword(accountDto.getPassword(), salt);
            String hashedPassword = optHashedPassword.orElseThrow(HashingException::new);

            user.setPassword(hashedPassword);
            user.setSalt(salt);

        } catch(HashingException e){
            e.printStackTrace();
        }
        return (userRepository.save(user));
    }

    public boolean validateUserCredentials(String username, String passwordToCheck) throws HashingException {
        User user = userRepository.findByUsername(username);

        Optional<String> optHashedPassword = Hashing.hashPassword(passwordToCheck, user.getSalt());
        String hashedPassword = optHashedPassword.orElseThrow(HashingException::new);

        return hashedPassword.equals(user.getPassword());
    }

    @Override
    public User findUserByEmail(final String email) {
        return userRepository.findByEmail(email);
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
}
