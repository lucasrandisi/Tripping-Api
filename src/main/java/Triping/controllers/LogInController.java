package Triping.controllers;

import Triping.services.IUserService;
import Triping.utils.GenericResponse;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;



@Controller
@RequestMapping(path="/login")
public class LogInController {
    @Autowired
    private IUserService userService;


    @PostMapping
    public @ResponseBody GenericResponse login(@RequestBody String data, HttpServletRequest req) {
        Gson gson = new Gson();
        UserDto userDto = gson.fromJson(data, UserDto.class);

        String username = userDto.getUsername();
        String password = userDto.getPassword();

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

