package Triping.services;

import Triping.models.User;
import Triping.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger logger = Logger.getLogger(CustomUserDetailsService.class.getName());

    @Autowired
    private UserRepository userRepository;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Authenticating request: "+ username);
        // TODO: 22.02.2017 Provide authentication both by login and 2fa

        final User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Profile is not found:" + username);
        }
        return user;
    }
}
