package Triping.controllers;

import Triping.models.User;
import Triping.services.IUserService;
import Triping.services.SignUpService;

import Triping.utils.exceptions.HashingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import com.google.gson.Gson;
import javax.validation.Valid;
import java.io.IOException;

@RestController

public class RegistrationController {
    @Autowired
    private SignUpService signUpService;

    private IUserService userService;

    @Autowired //Triggers async execution of tasks
    ApplicationEventPublisher eventPublisher;


    //Registration
    @PostMapping(path="/register")
    @ResponseBody
    public String registerUserAccount(@RequestParam("data") String data) {

        /*Json to UserDto mapping*/
        Gson gson = new Gson();
        UserDto accountDto = gson.fromJson(data, UserDto.class);

        try {
            final User registered = userService.registerNewUserAccount(accountDto);
        } catch (HashingException e) {
            e.printStackTrace();
        }
        //Todo: Enviar token de validacion de email
        return ("success");
    }
}