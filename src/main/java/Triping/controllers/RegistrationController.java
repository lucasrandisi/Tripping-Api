package Triping.controllers;

import Triping.services.IUserService;
import Triping.utils.GenericResponse;
import Triping.utils.exceptions.NotImplementedException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
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
    @PostMapping(path="/user/register")
    //@ResponseStatus(HttpStatus.CREATED)
    public GenericResponse registerUserAccount(@RequestBody String data) {

        /*Json to UserDto mapping*/
        Gson gson = new Gson();
        UserDto accountDto = gson.fromJson(data, UserDto.class);

        try {
            userService.registerNewUserAccount(accountDto);

            //Todo: Enviar token de validacion de email
            return new GenericResponse("success");
        }
        catch (Exception e){
            return new GenericResponse(e.getMessage());
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