package Triping.services.specifications;

import Triping.dto.*;
import Triping.models.User;
import Triping.models.VerificationToken;
import Triping.utils.exceptions.*;

import java.util.List;
import java.util.Set;

public interface IUserService {

    User findUserByEmail(final String email);

    User findUserByUsername(final String username);

    void saveUser(User user);

    void followUser(User currentUser, String username) throws ResourceNotFoundException, AlredyAddedException, SameEntityException;

    void unfollowUser(User currentUser, String username) throws ResourceNotFoundException, SameEntityException;

    void addInterest(User currentUser, String id) throws ResourceNotFoundException, AlredyAddedException;

    Set<InterestDto> getInterests(User currentUser);

    void removeInterest(User currentUser, String id) throws ResourceNotFoundException;

    UserDto getProfile(String username) throws ResourceNotFoundException;

    List<UserDto> getFollowed(String username) throws ResourceNotFoundException;

    List<UserDto> getFollowers(String username) throws ResourceNotFoundException;

    List<TripDto> getTrips(String username, String title) throws ResourceNotFoundException;

    TripDto getTrip(String username, String tripID) throws ResourceNotFoundException;

}
