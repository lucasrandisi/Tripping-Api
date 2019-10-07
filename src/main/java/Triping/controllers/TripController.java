package Triping.controllers;

import Triping.utils.GenericResponse;
import Triping.utils.exceptions.NotImplementedException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class TripController {

    /*
    @Autowired
    private ITripService tripService;
    */

    @PostMapping(path="/trip/new")
    public GenericResponse createNewTrip(){
        throw new NotImplementedException();
    }

    @GetMapping(path="/trip/{id}")
    public GenericResponse findOne(@PathVariable Long id){
        throw new NotImplementedException();
    }

    // Save or update
    @PutMapping("/trip/{id}")
    public GenericResponse saveOrUpdate(@PathVariable Long id) {
        throw new NotImplementedException();
    }

    @DeleteMapping("/trip/{id}")
    void deleteTrip(@PathVariable Long id) {
        throw new NotImplementedException();
    }

    @PostMapping(path="/trip/{id}/invite")
    public GenericResponse inviteFriendsToTrip(){
        throw new NotImplementedException();
    }

}
