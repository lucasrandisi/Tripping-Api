package Triping.services;

import Triping.models.CustomUserDetails;
import Triping.models.User;
import Triping.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String usernameToSearch) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(usernameToSearch);

        CustomUserDetails customUserDetails = new CustomUserDetails(user);

        return  customUserDetails;
    }
}
