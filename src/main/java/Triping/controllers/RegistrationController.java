package Triping.controllers;

import Triping.models.User;
import Triping.services.IUserService;
import Triping.utils.GenericResponse;
import Triping.utils.exceptions.NotImplementedException;
import Triping.utils.exceptions.HashingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;


@RestController
@CrossOrigin
public class RegistrationController {

    @Autowired
    private IUserService userService;

    @Autowired //Triggers async execution of tasks
    ApplicationEventPublisher eventPublisher;

    //Registration
    @PostMapping(path="/user/register")
    public ResponseEntity<?> registerUserAccount(@Valid @RequestBody UserDto accountDto) {
        try {
            final User registered = userService.registerNewUserAccount(accountDto);
            //Todo: Enviar token de validacion de email

            URI location = ServletUriComponentsBuilder.fromPath("user/{username}")
                    .buildAndExpand(registered.getUsername()).toUri();

            return ResponseEntity.created(location).build();
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /*  When the user clicks on the verification link, the verification token is purged and the account is activated
     */
    @GetMapping(path="/registrationConfirm")
    public ResponseEntity<?> confirmRegistration(){
        throw new NotImplementedException();
    }

    @RequestMapping(value = "/user/resendRegistrationToken")
    @ResponseBody
    public GenericResponse resendRegistrationToken(){
        throw new NotImplementedException();
    }

    @PostMapping(path="/user/resetPassword")
    public GenericResponse resetPassword(){
        throw new NotImplementedException();
    }

    @PostMapping(path="/user/changePassword")
    public GenericResponse changePassword(){
        throw new NotImplementedException();
    }
}