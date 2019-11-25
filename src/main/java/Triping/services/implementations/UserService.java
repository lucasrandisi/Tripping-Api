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
    private VerificationTokenRepository tokenRepository;

    @Autowired
    private InterestRepository interestRepository;

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    // ----------------   Registration    ----------------
    @Override
    public User registerNewUserAccount(final AccountDto accountDto) {
        if (this.findUserByEmail(accountDto.getEmail()) != null) {
            throw new UserAlreadyExistsException("Ya existe una cuenta registrada con " + accountDto.getEmail());
        }

        if (this.findUserByUsername(accountDto.getUsername()) != null) {
            throw new UserAlreadyExistsException("Ya existe una cuenta registrada con nombre de usuario " + accountDto.getUsername());
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

    @Override
    public VerificationToken getVerificationToken(String verificationToken) {
        return tokenRepository.findByToken(verificationToken);
    }

    @Override
    public void ResendVerificationToken(User currentUser) throws AlredyEnabledException {
        if(currentUser.isEnabled()){
            throw new AlredyEnabledException("El usuario ya se encuentra habilitao");
        }

        VerificationToken expiredToken = tokenRepository.findByUser(currentUser);
        expiredToken.updateToken();

        String appUrl = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();
        applicationEventPublisher.publishEvent(new OnRegistrationCompleteEvent(currentUser, appUrl, expiredToken));
    }


    @Override
    public void createVerificationToken(final User user, final String token) {
        VerificationToken verificationToken = new VerificationToken(user, token);
        tokenRepository.save(verificationToken);
    }

    // ----------------   Log in    ----------------
    public boolean validatePassword(String username, String password){
        User user = userRepository.findByUsername(username);
        if (user == null){
            return false;
        }

        String hashedPassword = user.getPassword();

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(password, hashedPassword);
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

        if(currentUser.getFriends().contains(toFollowUser)) {
            throw new AlredyAddedException("Ya sigue a este usuario");
        }

        currentUser.getFriends().add(toFollowUser);
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

        if(!currentUser.getFriends().contains(toUnfollowUser)) {
            throw new ResourceNotFoundException("Error al intentar dejar de seguir un usuario al que no se sigue");
        }

        currentUser.getFriends().remove(toUnfollowUser);
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

    // ----------------   User's Data   ----------------
    @Override
    public UserDto getProfile(String username) throws ResourceNotFoundException {
        User userToFind = findUserByUsername(username);

        if (userToFind == null) {
            throw new ResourceNotFoundException("Usuario no encontrado");
        }

        UserDto userDto = new UserDto();
        userDto.setNombre(userToFind.getNombre());
        userDto.setApellido(userToFind.getApellido());
        userDto.setEmail(userToFind.getEmail());
        userDto.setUserImage(userToFind.getUserImage());

        return userDto;
    }

    @Override
    public List<UserDto> getFollowed(String username) throws ResourceNotFoundException {
        User userToFind = findUserByUsername(username);

        if (userToFind == null) {
            throw new ResourceNotFoundException("Usuario no encontrado");
        }

        List<UserDto> followedUsers = new ArrayList<>();
        for(User u : userToFind.getFriends()){
            UserDto userDto = new UserDto();

            userDto.setId(u.getUserId());
            userDto.setNombre(u.getNombre());
            userDto.setApellido(u.getApellido());

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
        for(User u : userToFind.getFriendOf()){
            UserDto userDto = new UserDto();

            userDto.setId(u.getUserId());
            userDto.setNombre(u.getNombre());
            userDto.setApellido(u.getApellido());

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
            TripDto tripDto = new TripDto();

            tripDto.setId(trip.getTripId());
            tripDto.setTitle(trip.getTitle());
            tripDto.setDescription(trip.getDescription());
            tripDto.setDepartureDate(trip.getDepartureDate());
            tripDto.setEndDate(trip.getEndDate());

            UserDto owner = new UserDto();
            owner.setId(trip.getOwner().getUserId());
            owner.setUsername(trip.getOwner().getUsername());

            tripDto.setOwner(owner);
            tripDto.setItineraries(trip.getItineraries());
            tripDto.setContributingUsers(trip.getContributingUsers());

            tripDtoList.add(tripDto);
        }

        return tripDtoList;
    }

    @Override
    public TripDto getTrip(String username, String tripID) throws ResourceNotFoundException {
        User userToFind = findUserByUsername(username);

        if (userToFind == null) {
            throw new ResourceNotFoundException("Usuario no encontrado");
        }

        long parsedTripID = Long.parseLong(tripID);
        Optional<Trip> optionalTriptrip = tripRepository.findByTripIdAndAccessibility(parsedTripID, Trip.accessType.PUBLIC);

        Trip trip = optionalTriptrip.orElseThrow(() -> new ResourceNotFoundException("Viaje no encontrado"));

        if (trip.getOwner() != userToFind) {
            throw new ResourceNotFoundException("El viaje no pertenece al usuario");
        }

        TripDto tripDto = new TripDto();
        tripDto.setId(trip.getTripId());
        tripDto.setTitle(trip.getTitle());
        tripDto.setDescription(trip.getDescription());
        tripDto.setDepartureDate(trip.getDepartureDate());
        tripDto.setEndDate(trip.getEndDate());

        UserDto owner = new UserDto();
        owner.setId(trip.getOwner().getUserId());
        owner.setUsername(trip.getOwner().getUsername());

        tripDto.setOwner(owner);
        tripDto.setItineraries(trip.getItineraries());
        tripDto.setContributingUsers(trip.getContributingUsers());

        return tripDto;
    }


    public void changePassword(User currentUser, PasswordDto passwordDto) throws SameEntityException {
        BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();

        if (bcryptPasswordEncoder.matches(passwordDto.getPassword(), currentUser.getPassword()) ){
            throw new SameEntityException("El nuevo valor no puede coincidir con el anterior");
        }

        String encryptedPassword = bcryptPasswordEncoder.encode(passwordDto.getPassword());
        currentUser.setPassword(encryptedPassword);
        userRepository.save(currentUser);
    }
}
