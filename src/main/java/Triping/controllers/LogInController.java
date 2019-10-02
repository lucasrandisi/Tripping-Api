package Triping.controllers;

import Triping.services.IUserService;
import Triping.utils.exceptions.HashingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path="/login")
public class LogInController {
    @Autowired
    private IUserService userService;

    @PostMapping
    public @ResponseBody String login(@RequestParam(value="usr") String username, @RequestParam(value="pwd") String password) {
        try {
            if( userService.validateUserCredentials(username, password) ){
                return "Usuario encontrado";
            }
            else{
                return "Ni idea ese sujeto";
            }
        } catch (HashingException e) {
            return "Error al intentar iniciar sesion";
        }
    }

}

