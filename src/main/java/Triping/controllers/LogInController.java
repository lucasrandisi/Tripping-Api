package Triping.controllers;

import Triping.services.IUserService;
import Triping.utils.exceptions.HashingException;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
@RequestMapping(path="/login")
public class LogInController {
    @Autowired
    private IUserService userService;

    @PostMapping
    public @ResponseBody String login(@RequestBody String data, HttpServletRequest request) {
        Gson gson = new Gson();
        UserDto userDto = gson.fromJson(data, UserDto.class);

        String username = userDto.getUsername();
        String password = userDto.getPassword();

        try {
            if( userService.validateUserCredentials(username, password) ){
                HttpSession session = request.getSession();
                session.setAttribute("logged", true);
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

