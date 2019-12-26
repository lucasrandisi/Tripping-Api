package Triping.services.implementations;

import Triping.dto.*;
import Triping.models.Interest;
import Triping.models.Trip;
import Triping.models.User;
import Triping.repositories.InterestRepository;
import Triping.repositories.TripRepository;
import Triping.repositories.UserRepository;
import Triping.services.specifications.IAccountService;
import Triping.services.specifications.IUserService;
import Triping.utils.exceptions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private IAccountService accountService;

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
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
    public void followUser(String username) throws ResourceNotFoundException {
        final User user = findUserByUsername(username);
        if (user == null) { throw new ResourceNotFoundException("Usuario no encontrado"); }

        final User authenticatedUser = userRepository.findByUsername(accountService.currentAuthenticatedUser());
        if (authenticatedUser.doesFollow(user)) {
            authenticatedUser.unFollow(user);
        } else {
            authenticatedUser.follow(user);
        }
    }

    @Override
    public Set<Interest> getInterests(){
        final User authenticatedUser = userRepository.findByUsername(accountService.currentAuthenticatedUser());
        return authenticatedUser.getUserInterests();
    }

    @Override
    public void addInterest(Long id) throws ResourceNotFoundException, AlredyAddedException {
        final User authenticatedUser = userRepository.findByUsername(accountService.currentAuthenticatedUser());

        Interest interest = interestRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Interés no encontrado")
        );

        if(!authenticatedUser.addInterest(interest)){
            throw new AlredyAddedException("El interés ya se encuentra agregado al usuario");
        }
    }

    public void removeInterest(Long id) throws ResourceNotFoundException {
        final User authenticatedUser = userRepository.findByUsername(accountService.currentAuthenticatedUser());

        Interest interest = interestRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Interés no encontrado")
        );

        if(!authenticatedUser.removeInterest(interest)){
            throw new ResourceNotFoundException("El interés no pertenece al usuario");
        }
    }

    @Override
    public UserDto getProfile(String username) throws ResourceNotFoundException {
        User user = findUserByUsername(username);

        if (user == null) {
            throw new ResourceNotFoundException("Usuario no encontrado");
        }
        return new UserDto(user);
    }

    @Override
    public Page<UserDto> findFollowedUsers(String username, String searchTerm, Pageable pageRequest) {
        final Page<User> following = userRepository.findFollowedUsers(username, searchTerm, pageRequest);
        final User authenticatedUser = userRepository.findByUsername(accountService.currentAuthenticatedUser());
        return following.map(p -> new UserDto(p, authenticatedUser));
    }

    @Override
    public Page<UserDto> findUserFollowers(String username, String searchTerm, Pageable pageRequest) {
        final Page<User> followers = userRepository.findUserFollowers(username, searchTerm, pageRequest);
        final User authenticatedUser = userRepository.findByUsername(accountService.currentAuthenticatedUser());
        return followers.map(p -> new UserDto(p, authenticatedUser));
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

        if (!trip.hasOwner(userToFind)) {
            throw new ResourceNotFoundException("El viaje no pertenece al usuario");
        }

        return new TripDto(trip);
    }
}
