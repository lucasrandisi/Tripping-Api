package Triping.controllers;

import Triping.dto.AccountDto;
import Triping.models.User;
import Triping.models.VerificationToken;
import Triping.services.IUserService;
import Triping.tasks.OnRegistrationCompleteEvent;
import Triping.utils.GenericResponse;
import Triping.utils.exceptions.NotImplementedException;

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

    @Autowired //Triggers async execution of backend tasks
    private ApplicationEventPublisher eventPublisher;

    /**
     *Registration of new account
     * @param accountDto
     * @return The URL of created user
     */
    @PostMapping(path="/user/register")
    public ResponseEntity<?> registerUserAccount(@Valid @RequestBody AccountDto accountDto) {
        try {
            final User registered = userService.registerNewUserAccount(accountDto);

            // Create confirmation token and send confirmation email
            String appUrl = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, appUrl));

            URI location = ServletUriComponentsBuilder.fromPath("user/{username}")
                    .buildAndExpand(registered.getUsername()).toUri();

            return ResponseEntity.created(location).build();
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     *  When user clicks on the verification link, the account is activated
     * @param token Unique verification token for the account
     * @return Http.OK if account was verified, Http.BAD_REQUEST if invalid or expired
     */
    @GetMapping(path="/verify")
    public ResponseEntity<?> confirmRegistration(@RequestParam("token") String token){

        VerificationToken verificationToken = userService.getVerificationToken(token);
        if (verificationToken == null) {
            return new ResponseEntity<>("Invalid token", HttpStatus.BAD_REQUEST);
        }
        if(verificationToken.isExpired()){
            return new ResponseEntity<>("Expired token", HttpStatus.BAD_REQUEST);
        }
        User user = verificationToken.getUser();
        user.setEnabled(true);
        userService.saveUser(user);
        //ToDo: Logear usuario y redirigir a inicio
        return new ResponseEntity<>("User account has been activated", HttpStatus.OK);
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