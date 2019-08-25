package Triping.service;

import Triping.models.User;
import Triping.repositories.UserRepository;
import Triping.utils.Hashing;
import Triping.utils.exceptions.HashingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SingInServiceImp implements SignInService {
    @Autowired
    private UserRepository userRepository;

    private final int SALT_LENGTH = 4;

    public void registerUser(String username, String password) throws HashingException {
        Optional<String> optSalt = Hashing.generateSalt(SALT_LENGTH);
        String salt = optSalt.orElseThrow(HashingException::new);

        Optional<String> optHashedPassword = Hashing.hashPassword(password, salt);
        String hashedPassword = optHashedPassword.orElseThrow(HashingException::new);

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(hashedPassword);
        newUser.setSalt(salt);
        userRepository.save(newUser);
    }
}
