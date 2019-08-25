package Triping.service;

import Triping.models.User;
import Triping.repositories.UserRepository;
import Triping.utils.Hashing;
import Triping.utils.exceptions.HashingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginServiceImp implements LoginService {
    @Autowired
    private UserRepository userRepository;

    private final int SALT_LENGTH = 4;

    public boolean validateUser(String username, String passwordToCheck) throws HashingException {
        User user = userRepository.findByUsername(username);

        Optional<String> optHashedPassword = Hashing.hashPassword(passwordToCheck, user.getSalt());
        String hashedPassword = optHashedPassword.orElseThrow(HashingException::new);

        return hashedPassword.equals(user.getPassword());
    }
}
