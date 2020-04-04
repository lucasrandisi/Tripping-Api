package Triping.controllers;

import Triping.dto.AuthDto;
import Triping.services.specifications.IAccountService;
import Triping.services.specifications.IUserService;
import Triping.utils.GenericResponse;

import Triping.utils.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@RestController
@RequestMapping(path="/auth")
public class LogInController {
    @Autowired
    private IAccountService accountService;


    @PostMapping
    public ResponseEntity<String> login(@Valid @RequestBody AuthDto authDto, HttpServletRequest req) throws ResourceNotFoundException {
        String username = authDto.getUsername();
        String password = authDto.getPassword();

        if (accountService.validateUsernameAndPassword(username, password)){
                Authentication authentication =  new UsernamePasswordAuthenticationToken(username, null, null);
                SecurityContextHolder.getContext().setAuthentication(authentication);

                return new ResponseEntity<>("Usuario loggeado", HttpStatus.OK);
        }
        else{
                return new ResponseEntity<>("Contraseña incorrecta", HttpStatus.UNAUTHORIZED);
        }
    }

}

