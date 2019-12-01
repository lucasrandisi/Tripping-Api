package Triping.controllers;

import Triping.dto.AccountDto;
import Triping.dto.EmailDto;
import Triping.dto.PasswordDto;
import Triping.models.User;
import Triping.models.VerificationToken;
import Triping.services.specifications.IAccountService;
import Triping.services.specifications.IUserService;
import Triping.tasks.OnRegistrationCompleteEvent;
import Triping.utils.GenericResponse;
import Triping.utils.exceptions.AlredyEnabledException;
import Triping.utils.exceptions.NotImplementedException;
import Triping.utils.exceptions.SameEntityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@CrossOrigin
@RequestMapping
public class AccountController {

    @Autowired
    IUserService userService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    IAccountService accountService;

    @GetMapping(path="/me")
    public ResponseEntity<?> getCurrentAuthenticatedUser(){
        String username = accountService.currentAuthenticatedUser();

        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("user/{username}").buildAndExpand(username).toUri();
        return ResponseEntity.ok(location);
    }

    /**
     *Registration of new account
     * @param accountDto
     * @return The URL of created user
     */
    @PostMapping(path="/register")
    public ResponseEntity<?> registerUserAccount(@Valid @RequestBody AccountDto accountDto) {
        try {
            final User registered = accountService.registerNewUserAccount(accountDto);

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

        VerificationToken verificationToken = accountService.getVerificationToken(token);
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

    @PostMapping(path="/resetPassword")
    public GenericResponse resetPassword(){
        throw new NotImplementedException();
    }


    @PostMapping(path="/changeEmail")
    public ResponseEntity<String> changeEmail(@Valid @RequestBody EmailDto emailDto) throws SameEntityException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findUserByUsername(auth.getPrincipal().toString());

        accountService.changeEmail(currentUser, emailDto);

        return new ResponseEntity<>("Email cambiado", HttpStatus.OK);
    }

    @PostMapping("/changePassword")
    public ResponseEntity<String> changePassword(@Valid @RequestBody PasswordDto passwordDto) throws SameEntityException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findUserByUsername(auth.getPrincipal().toString());

        accountService.changePassword(currentUser, passwordDto);

        return new ResponseEntity<>("Contraseña cambiada", HttpStatus.OK);
    }

    @PostMapping(path = "/resendRegistrationToken")
    public ResponseEntity<String> resendRegistrationToken() throws AlredyEnabledException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findUserByUsername(auth.getPrincipal().toString());

        accountService.ResendVerificationToken(currentUser);

        return new ResponseEntity<>("Email reenviado", HttpStatus.OK);
    }
}
