package Triping.controllers;

import Triping.models.Trip;
import Triping.models.TripParty;
import Triping.models.User;
import Triping.services.ITripService;
import Triping.services.IUserService;
import Triping.utils.ErrorDetails;
import Triping.utils.GenericResponse;
import Triping.utils.exceptions.NotImplementedException;

import Triping.utils.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/trips")
public class TripController {

    @Autowired
    private ITripService tripService;

    @Autowired
    private IUserService userService;

    @GetMapping
    public List<Trip> getAllTrips() {
        return tripService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trip> getTripById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        Trip trip = tripService.getOne(id, getAuthenticatedUser());
        return ResponseEntity.ok().body(trip);
    }

    @PostMapping
    public Trip createNewTrip(@Valid @RequestBody Trip trip){
        final User authenticatedUser = this.getAuthenticatedUser();
        trip.setOwner(authenticatedUser);
        return tripService.createNewTrip(trip, authenticatedUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Trip> updateTrip(@PathVariable(value = "id") Long id, @Valid @RequestBody Trip tripDetails) throws ResourceNotFoundException {
        final User authenticatedUser = this.getAuthenticatedUser();

        final Trip updatedTrip = tripService.updateTrip(id, tripDetails, authenticatedUser);
        return ResponseEntity.ok().body(updatedTrip);
    }

    @DeleteMapping("{id}")
    public Map<String, Boolean> deleteTrip(@PathVariable Long id) throws ResourceNotFoundException {
        final User authenticatedUser = this.getAuthenticatedUser();
        tripService.deleteTrip(id, authenticatedUser);

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    @PutMapping("/{id}/party/{username}")
    public GenericResponse addContributor(@PathVariable Long id, @PathVariable String username) throws ResourceNotFoundException {
        final User authenticatedUser = this.getAuthenticatedUser();

        final Trip trip = tripService.getOne(id,authenticatedUser);
        final User invitee = userService.findUserByUsername(username);
        if(trip.isOwner(authenticatedUser)){
            tripService.addContributorToTrip(trip, invitee);
            return new GenericResponse("Usuario invitado correctamente y esta pendiente de confirmacion");
        }
        else{
            throw new NotImplementedException();
        }
    }

    @DeleteMapping("/{id}/party/{username}")
    public GenericResponse removeContributor(@PathVariable Long id, @PathVariable String username) throws ResourceNotFoundException {
        final User authenticatedUser = this.getAuthenticatedUser();

        final Trip trip = tripService.getOne(id,authenticatedUser);
        final User contributor = userService.findUserByUsername(username);
        if(trip.isOwner(authenticatedUser)){
            tripService.removeContributorFromTrip(trip, contributor);
            return new GenericResponse("Usuario eliminado correctamente");
        }
        else{
            throw new NotImplementedException();
        }
    }

    @PostMapping(path="/{id}/invite")
    public GenericResponse inviteFriendsToTrip(){
        throw new NotImplementedException();
    }

    public User getAuthenticatedUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userService.findUserByUsername(auth.getPrincipal().toString());
    }
}
