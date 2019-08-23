package Triping.controllers;

import Triping.models.User;
import Triping.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller // Serves as a specialization of @Component, allowing for implementation classes to be autodetected through classpath scanning
public class Login {
    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/login")
    public User login(@RequestParam(value="usr") String username, @RequestParam(value="pwd") String password) {

        return new User();
    }

    @RequestMapping("/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        // This returns a JSON or XML with the users
        return userRepository.findAll();
    }
}

