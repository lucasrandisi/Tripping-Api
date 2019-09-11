package Triping.controllers;

import Triping.models.User;
import Triping.services.IUserService;

import Triping.utils.exceptions.HashingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import com.google.gson.Gson;

@RestController
@CrossOrigin
public class RegistrationController {

    @Autowired
    private IUserService userService;

    @Autowired //Triggers async execution of tasks
    ApplicationEventPublisher eventPublisher;

    //Registration
    @PostMapping(path="/register")
    //@ResponseStatus(HttpStatus.CREATED)
    public String registerUserAccount(@RequestBody String data) {

        /*Json to UserDto mapping*/
        Gson gson = new Gson();
        UserDto accountDto = gson.fromJson(data, UserDto.class);

        try {
            final User registered = userService.registerNewUserAccount(accountDto);
        } catch (HashingException e) {
            e.printStackTrace();
        } catch (Exception e){
            return(e.getMessage());
        }
        //Todo: Enviar token de validacion de email
        return ("success");
    }
}