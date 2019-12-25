package Triping.services.specifications;

import Triping.dto.*;
import Triping.models.Interest;
import Triping.models.User;
import Triping.utils.exceptions.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface IUserService {

    User findUserByEmail(final String email);

    User findUserByUsername(final String username);

    void saveUser(User user);

    void followUser(String username) throws ResourceNotFoundException, AlredyAddedException, SameEntityException;

    void unFollowUser(String username) throws ResourceNotFoundException, SameEntityException;

    void addInterest(Long id) throws ResourceNotFoundException, AlredyAddedException;

    Set<Interest> getInterests();

    void removeInterest(Long id) throws ResourceNotFoundException;

    UserDto getProfile(String username) throws ResourceNotFoundException;

    Page<UserDto> findFollowedUsers(String username, String searchTerm, Pageable pageRequest);

    Page<UserDto> findUserFollowers(String username, String searchTerm, Pageable pageRequest);

    List<TripDto> getTrips(String username, String title) throws ResourceNotFoundException;

    TripDto getTrip(String username, Long tripID) throws ResourceNotFoundException;

}
