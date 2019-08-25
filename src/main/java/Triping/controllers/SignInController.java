package Triping.controllers;

import Triping.service.SignInService;
import Triping.utils.exceptions.HashingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path="/signIn")
public class SignInController {
    @Autowired
    private SignInService signInService;

    @PostMapping
    public @ResponseBody String addNewUser(@RequestParam String usr, @RequestParam String pwd) {
        try {
            signInService.registerUser(usr, pwd);
            return "Saved";
        } catch (HashingException e) {
            return "Error al registrar el usuario";
        }

    }
}