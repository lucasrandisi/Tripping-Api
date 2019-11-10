package Triping.controllers;

import Triping.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private IUserService userService;
/*
    @PostMapping("/follow/{username}")
    public void followUser(@PathVariable String username){
        try {
            userService.followUser(username);
        }
        catch(Exception e){

        }
    }

    @DeleteMapping("/unfollow/{username}")
    public void unfollowUser(@PathVariable String username){

    }
    */
}
