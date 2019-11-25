package Triping.controllers;

import Triping.dto.AuthDto;
import Triping.services.specifications.IUserService;
import Triping.utils.GenericResponse;

import org.springframework.beans.factory.annotation.Autowired;
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
    private IUserService userService;


    @PostMapping
    public @ResponseBody GenericResponse login(@Valid @RequestBody AuthDto authDto, HttpServletRequest req) {
        String username = authDto.getUsername();
        String password = authDto.getPassword();

        try {
            if (userService.validatePassword(username, password)){
                Authentication authentication =  new UsernamePasswordAuthenticationToken(username, null, null);
                SecurityContextHolder.getContext().setAuthentication(authentication);

                return new GenericResponse("Usuario loggeado");
            }
            else{
                return new GenericResponse("Ni idea ese sujeto");
            }
        } catch (Exception e) {
            return new GenericResponse("Ocurrió un error");
        }

    }

}

