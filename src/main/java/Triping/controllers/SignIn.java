package Triping.controllers;

import Triping.models.User;
import Triping.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path="/Signin")
public class SignIn {
    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public @ResponseBody String addNewUser (@RequestParam String usr, @RequestParam String pwd) {
        User newUser = new User();
        newUser.setUsername(usr);
        newUser.setPassword(pwd);
        userRepository.save(newUser);
        return "Saved";
    }


}
