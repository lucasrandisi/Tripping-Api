package Triping.controllers;

import Triping.services.SignUpService;
import Triping.utils.exceptions.HashingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/user/registration")
public class SignUpController {
    @Autowired
    private SignUpService signUpService;

    @PostMapping
    public @ResponseBody String addNewUser(@RequestParam String usr, @RequestParam String pwd) {
        try {
            signUpService.registerUser(usr, pwd);
            return "Saved";
        } catch (HashingException e) {
            return "Error al registrar el usuario";
        }

    }
}