package Triping.controllers;

import Triping.dto.InterestDto;
import Triping.models.User;
import Triping.services.IUserService;
import Triping.utils.exceptions.AlredyAddedException;
import Triping.utils.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/user")
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


    @GetMapping("/interests")
    public ResponseEntity<Set<InterestDto>> getInterests(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findUserByUsername(auth.getPrincipal().toString());

        Set<InterestDto> userInterests = userService.getInterests(currentUser);

        return new ResponseEntity<>(userInterests, HttpStatus.OK);
    }


    @PostMapping("/interests/{id}")
    public ResponseEntity<String> addInterest(@PathVariable String id) throws ResourceNotFoundException, AlredyAddedException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findUserByUsername(auth.getPrincipal().toString());

        userService.addInterest(currentUser, id);

        return new ResponseEntity<>("Interes agregado", HttpStatus.OK);
    }

    @DeleteMapping("/interests/{id}")
    public ResponseEntity<String> removeInterest(@PathVariable String id) throws ResourceNotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findUserByUsername(auth.getPrincipal().toString());

        userService.removeInterest(currentUser, id);

        return new ResponseEntity<>("Interés removido", HttpStatus.OK);
    }

}
