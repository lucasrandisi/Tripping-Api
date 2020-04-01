package Triping.controllers;

import Triping.dto.*;
import Triping.services.specifications.IUserService;
import Triping.utils.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private IUserService userService;

    /***
     * This endpoint is idempotent, meaning that it can either create or delete
     * a following relation between 2 users
     * @param username of the user to (un)follow
     * @throws ResourceNotFoundException when the user to (un)follow does not exist
     */
    @PutMapping("user/follow/{username}")
    @ResponseStatus(HttpStatus.OK)
    public void followUser(@PathVariable String username) throws ResourceNotFoundException {
        userService.followUser(username);
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

        return userService.findFollowedUsers(username, searchTerm, pageRequest);
    }

    @GetMapping("/{username}/followers")
    public Page<UserDto> findUserFollowers(
            @PathVariable String username,
            @RequestParam(name = "search", defaultValue = "", required = false) String searchTerm,
            @PageableDefault(size = 20) Pageable pageRequest){

        return userService.findUserFollowers(username, searchTerm, pageRequest);
    }
}
