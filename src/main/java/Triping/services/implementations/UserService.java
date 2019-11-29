package Triping.services.implementations;

import Triping.dto.*;
import Triping.models.Interest;
import Triping.models.Trip;
import Triping.models.User;
import Triping.models.VerificationToken;
import Triping.repositories.InterestRepository;
import Triping.repositories.TripRepository;
import Triping.repositories.UserRepository;
import Triping.repositories.VerificationTokenRepository;
import Triping.services.specifications.IUserService;
import Triping.tasks.OnRegistrationCompleteEvent;
import Triping.utils.exceptions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.*;

@Service
@Transactional
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InterestRepository interestRepository;

    @Autowired
    private TripRepository tripRepository;



    // ----------------   Save    ----------------

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }



    // ----------------   Find methods    ----------------
    @Override
    public User findUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }



    // ----------------   Follow    ----------------

    @Override
    public void followUser(User currentUser, String toFollowUsername) throws ResourceNotFoundException, AlredyAddedException, SameEntityException {
        User toFollowUser = findUserByUsername(toFollowUsername);

        if (toFollowUser == null) {
            throw new ResourceNotFoundException("Usuario no encontrado");
        }

        if (currentUser == toFollowUser){
            throw new SameEntityException("Error al intentar seguir a uno mismo");
        }

        if(currentUser.getFollowing().contains(toFollowUser)) {
            throw new AlredyAddedException("Ya sigue a este usuario");
        }

        currentUser.getFollowing().add(toFollowUser);
        userRepository.save(currentUser);

    }

    @Override
    public void unfollowUser(User currentUser, String toUnfollowUsername) throws ResourceNotFoundException, SameEntityException {
        User toUnfollowUser = findUserByUsername(toUnfollowUsername);

        if (toUnfollowUser == null) {
            throw new ResourceNotFoundException("Usuario no encontrado");
        }

        if (currentUser == toUnfollowUser) {
            throw new SameEntityException("Error al intentar dejar de seguir a uno mismo");
        }

        if(!currentUser.getFollowing().contains(toUnfollowUser)) {
            throw new ResourceNotFoundException("Error al intentar dejar de seguir un usuario al que no se sigue");
        }

        currentUser.getFollowing().remove(toUnfollowUser);
    }

    // ----------------   Interests   ----------------

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
    public void addInterest(User currentUser,Long id) throws ResourceNotFoundException, AlredyAddedException {
        Optional<Interest> interestSearch = interestRepository.findById(id);

        Interest interestToAdd = interestSearch.orElseThrow(() -> new ResourceNotFoundException("Interés no encontrado"));

        if(currentUser.getUserInterests().contains(interestToAdd)){
            throw new AlredyAddedException("El interés ya se encuentra agregado al usuario");
        }

        currentUser.getUserInterests().add(interestToAdd);

        userRepository.save(currentUser);
    }

    public void removeInterest(User currentUser, Long id) throws ResourceNotFoundException {
        Optional<Interest> interestSearch = interestRepository.findById(id);

        Interest interestToRemove = interestSearch.orElseThrow(() -> new ResourceNotFoundException("Interés no encontrado"));

        if(!currentUser.getUserInterests().contains((interestToRemove))){
            throw new ResourceNotFoundException("El interés no pertenece al usuario");
        }

        currentUser.getUserInterests().remove(interestToRemove);
    }

    // ----------------   User's Data   ----------------
    @Override
    public UserDto getProfile(String username) throws ResourceNotFoundException {
        User user = findUserByUsername(username);

        if (user == null) {
            throw new ResourceNotFoundException("Usuario no encontrado");
        }
        return new UserDto(user);
    }

    @Override
    public List<UserDto> getFollowed(String username) throws ResourceNotFoundException {
        User userToFind = findUserByUsername(username);

        if (userToFind == null) {
            throw new ResourceNotFoundException("Usuario no encontrado");
        }

        List<UserDto> followedUsers = new ArrayList<>();
        for(User user : userToFind.getFollowing()){
            UserDto userDto = new UserDto(user);
            followedUsers.add(userDto);
        }
        return followedUsers;
    }

    @Override
    public List<UserDto> getFollowers(String username) throws ResourceNotFoundException {
        User userToFind = findUserByUsername(username);

        if (userToFind == null) {
            throw new ResourceNotFoundException("Usuario no encontrado");
        }

        List<UserDto> followers = new ArrayList<>();
        for(User user : userToFind.getFollowers()){
            UserDto userDto = new UserDto(user);
            followers.add(userDto);
        }
        return followers;
    }

    @Override
    public List<TripDto> getTrips(String username, String title) throws ResourceNotFoundException {
        User userToFind = findUserByUsername(username);

        if (userToFind == null) {
            throw new ResourceNotFoundException("Usuario no encontrado");
        }

        List<Trip> publicTripsList;
        if(title != null){
            publicTripsList = tripRepository.findByOwnerAndAccessibilityAndTitleContaining(userToFind, Trip.accessType.PUBLIC, title);
        }
        else {
            publicTripsList = tripRepository.findByOwnerAndAccessibility(userToFind, Trip.accessType.PUBLIC);
        }

        List<TripDto> tripDtoList = new ArrayList<>();

        for (Trip trip : publicTripsList){
            TripDto tripDto = new TripDto(trip);
            tripDtoList.add(tripDto);
        }
        return tripDtoList;
    }

    @Override
    public TripDto getTrip(String username, Long tripId) throws ResourceNotFoundException {
        User userToFind = findUserByUsername(username);

        if (userToFind == null) {
            throw new ResourceNotFoundException("Usuario no encontrado");
        }

        Optional<Trip> optionalTrip = tripRepository.findByTripIdAndAccessibility(tripId, Trip.accessType.PUBLIC);

        Trip trip = optionalTrip.orElseThrow(() -> new ResourceNotFoundException("Viaje no encontrado"));

        if (trip.getOwner() != userToFind) {
            throw new ResourceNotFoundException("El viaje no pertenece al usuario");
        }

        return new TripDto(trip);
    }



}
