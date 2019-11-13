package Triping.controllers;

import Triping.dto.InterestDto;
import Triping.dto.UserDto;
import Triping.models.User;
import Triping.services.IUserService;
import Triping.utils.exceptions.AlredyAddedException;
import Triping.utils.exceptions.ResourceNotFoundException;
import Triping.utils.exceptions.SameEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
public class UserController {

    @Autowired
    private IUserService userService;


    // ------------------ Follow ------------------

    @PostMapping("user/follow/{toFollowUsername}")
    public ResponseEntity<String> followUser(@PathVariable String toFollowUsername) throws AlredyAddedException, SameEntityException, ResourceNotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findUserByUsername(auth.getPrincipal().toString());

        userService.followUser(currentUser, toFollowUsername);

        return new ResponseEntity<>("Usuario seguido", HttpStatus.OK);
    }



    @DeleteMapping("user/unfollow/{toUnfollowUsername}")
    public ResponseEntity<String> unfollowUser(@PathVariable String toUnfollowUsername) throws ResourceNotFoundException, SameEntityException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findUserByUsername(auth.getPrincipal().toString());

        userService.unfollowUser(currentUser,toUnfollowUsername);

        return new ResponseEntity<>("Usuario unfollowed", HttpStatus.OK);

    }

    // ------------------ Interests ------------------

    @GetMapping("/user/interests")
    public ResponseEntity<Set<InterestDto>> getInterests(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findUserByUsername(auth.getPrincipal().toString());

        Set<InterestDto> userInterests = userService.getInterests(currentUser);

        return new ResponseEntity<>(userInterests, HttpStatus.OK);
    }


    @PostMapping("user/interests/{id}")
    public ResponseEntity<String> addInterest(@PathVariable String id) throws ResourceNotFoundException, AlredyAddedException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findUserByUsername(auth.getPrincipal().toString());

        userService.addInterest(currentUser, id);

        return new ResponseEntity<>("Interes agregado", HttpStatus.OK);
    }

    @DeleteMapping("user/interests/{id}")
    public ResponseEntity<String> removeInterest(@PathVariable String id) throws ResourceNotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findUserByUsername(auth.getPrincipal().toString());

        userService.removeInterest(currentUser, id);

        return new ResponseEntity<>("Interés removido", HttpStatus.OK);
    }


    // ------------------ User's data ------------------
    @GetMapping("/{username}/profile")
    public ResponseEntity<UserDto> profile(@PathVariable String username) throws ResourceNotFoundException {
        UserDto userProfile = userService.getProfile(username);

        return new ResponseEntity<>(userProfile, HttpStatus.OK);
    }

    @GetMapping("/{username}/followed")
    public ResponseEntity<List<UserDto>> followed(@PathVariable String username) throws  ResourceNotFoundException {
        List<UserDto> userFriends = userService.getFollowed(username);

        return new ResponseEntity<>(userFriends, HttpStatus.OK);
    }

    @GetMapping("/{username}/followers")
    public ResponseEntity<List<UserDto>> followers(@PathVariable String username) throws  ResourceNotFoundException {
        List<UserDto> userFriends = userService.getFollowers(username);

        return new ResponseEntity<>(userFriends, HttpStatus.OK);
    }
}
