package Triping.controllers;

import Triping.dto.*;
import Triping.models.Interest;
import Triping.models.User;
import Triping.services.specifications.IUserService;
import Triping.utils.exceptions.AlredyAddedException;
import Triping.utils.exceptions.ResourceNotFoundException;
import Triping.utils.exceptions.SameEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping("user/follow/{username}")
    public ResponseEntity<String> followUser(@PathVariable String username) throws AlredyAddedException, SameEntityException, ResourceNotFoundException {
        userService.followUser(username);
        return new ResponseEntity<>("Usuario seguido", HttpStatus.OK);
    }

    @DeleteMapping("user/follow/{username}")
    public ResponseEntity<String> unFollowUser(@PathVariable String username) throws ResourceNotFoundException, SameEntityException {
        userService.unFollowUser(username);
        return new ResponseEntity<>("Usuario unfollowed", HttpStatus.OK);
    }

    @GetMapping("/user/interests")
    public ResponseEntity<Set<Interest>> getInterests() {
        Set<Interest> userInterests = userService.getInterests();
        return new ResponseEntity<>(userInterests, HttpStatus.OK);
    }


    @PostMapping("user/interests/{id}")
    public ResponseEntity<String> addInterest(@PathVariable("id") Long interestId) throws ResourceNotFoundException, AlredyAddedException {
        userService.addInterest(interestId);
        return new ResponseEntity<>("Interes agregado", HttpStatus.OK);
    }

    @DeleteMapping("user/interests/{id}")
    public ResponseEntity<String> removeInterest(@PathVariable("id") Long interestId) throws ResourceNotFoundException {
        userService.removeInterest(interestId);
        return new ResponseEntity<>("Interés removido", HttpStatus.OK);
    }

    @GetMapping("/{username}/profile")
    public ResponseEntity<UserDto> profile(@PathVariable String username) throws ResourceNotFoundException {
        UserDto userProfile = userService.getProfile(username);
        return new ResponseEntity<>(userProfile, HttpStatus.OK);
    }

    /**
     * Endpoint with pageable and sortable payload for getting user's followed users
     * Example URI: /user1/followed?search=pete&page=2&size=5&order=username,asc
     * @param username of the user to search
     * @param searchTerm optional partial description that filters usernames of followed users
     * @param pageRequest
     */
    @GetMapping("/{username}/followed")
    public Page<UserDto> findFollowedUsers(
            @PathVariable String username,
            @RequestParam(name = "search", defaultValue = "", required = false) String searchTerm,
            @PageableDefault(size = 20) Pageable pageRequest){

        final Page<User> userFriends = userService.findFollowedUsers(username, searchTerm, pageRequest);
        return userFriends.map(UserDto::new);
    }

    @GetMapping("/{username}/followers")
    public Page<UserDto> findUserFollowers(
            @PathVariable String username,
            @RequestParam(name = "search", defaultValue = "", required = false) String searchTerm,
            @PageableDefault(size = 20) Pageable pageRequest){

        final Page<User> userFriends = userService.findUserFollowers(username, searchTerm, pageRequest);
        return userFriends.map(UserDto::new);
    }

    @GetMapping("/{username}/trips/{id}")
    public ResponseEntity<TripDto> userTrip(@PathVariable String username, @PathVariable("id") Long tripId) throws ResourceNotFoundException {
        TripDto trip = userService.getTrip(username, tripId);
        return new ResponseEntity<>(trip, HttpStatus.OK);
    }

    @GetMapping("/{username}/trips")
    public ResponseEntity<List<TripDto>> userTrips(@PathVariable String username, @RequestParam(required = false) String title) throws ResourceNotFoundException {
        List<TripDto> userFriends = userService.getTrips(username, title);
        return new ResponseEntity<>(userFriends, HttpStatus.OK);
    }

}
