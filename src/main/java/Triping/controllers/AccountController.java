package Triping.controllers;


import Triping.dto.EmailDto;
import Triping.dto.PasswordDto;
import Triping.models.User;
import Triping.services.specifications.IAccountService;
import Triping.services.specifications.IUserService;
import Triping.utils.GenericResponse;
import Triping.utils.exceptions.AlredyEnabledException;
import Triping.utils.exceptions.NotImplementedException;
import Triping.utils.exceptions.SameEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path="/user")
public class AccountController {

    @Autowired
    IUserService userService;

    @Autowired
    IAccountService accountService;

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
