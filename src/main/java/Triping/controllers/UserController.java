package Triping.controllers;

import Triping.models.CustomUserDetails;
import Triping.models.User;
import Triping.services.IUserService;
import Triping.utils.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping("/follow/{username}")
    public String followUser(@PathVariable String username){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findUserByUsername(auth.getPrincipal().toString());

        try {
            userService.followUser(currentUser, username);
            return "Agregado";
        }
        catch(ResourceNotFoundException e){
            return e.getMessage();
        }

    }

    @DeleteMapping("/unfollow/{username}")
    public String unfollowUser(@PathVariable String username){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findUserByUsername(auth.getPrincipal().toString());

        try {
            userService.unfollowUser(currentUser,username);
            return "Unfollowed";
        }
        catch(ResourceNotFoundException e){
            return e.getMessage();
        }

    }

}
