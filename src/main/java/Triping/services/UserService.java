package Triping.services;

import Triping.dto.AccountDto;
import Triping.dto.InterestDto;
import Triping.models.Interest;
import Triping.models.User;
import Triping.models.VerificationToken;
import Triping.repositories.InterestRepository;
import Triping.repositories.UserRepository;
import Triping.repositories.VerificationTokenRepository;
import Triping.utils.exceptions.AlredyAddedException;
import Triping.utils.exceptions.ResourceNotFoundException;
import Triping.utils.exceptions.UserAlreadyExistException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class UserService implements IUserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Autowired
    private InterestRepository interestRepository;


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


    @Override
    public Set<InterestDto> getInterests(User currentUser){
        Set<InterestDto> userInterests = new LinkedHashSet<>();

        for(Interest interest : currentUser.getUserInterests()){
            String description = interest.getDescription();
            Long id = interest.getId();
            userInterests.add( new InterestDto(id, description));
        }

        return userInterests;
    }

    @Override
    public void addInterest(User currentUser, String id) throws ResourceNotFoundException, AlredyAddedException {
        long interestID = Long.parseLong(id);
        Optional<Interest> interestSearch = interestRepository.findById(interestID);

        Interest interestToAdd = interestSearch.orElseThrow(() -> new ResourceNotFoundException("Interés no encontrado"));

        if(currentUser.getUserInterests().contains(interestToAdd)){
            throw new AlredyAddedException("El interés ya se encuentra agregado al usuario");
        }

        currentUser.getUserInterests().add(interestToAdd);

        userRepository.save(currentUser);
    }

    public void removeInterest(User currentUser, String id) throws ResourceNotFoundException {
        long interestID = Long.parseLong(id);
        Optional<Interest> interestSearch = interestRepository.findById(interestID);

        Interest interestToRemove = interestSearch.orElseThrow(() -> new ResourceNotFoundException("Interés no encontrado"));

        if(!currentUser.getUserInterests().contains((interestToRemove))){
            throw new ResourceNotFoundException("El interés no pertenece al usuario");
        }

        currentUser.getUserInterests().remove(interestToRemove);
    }
}
