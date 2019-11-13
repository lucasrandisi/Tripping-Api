package Triping.controllers;

import Triping.models.InvitationToken;
import Triping.models.Trip;
import Triping.models.TripParty;
import Triping.models.User;
import Triping.services.ITripService;
import Triping.services.IUserService;
import Triping.utils.ErrorDetails;
import Triping.utils.GenericResponse;
import Triping.utils.exceptions.AccessDeniedException;
import Triping.utils.exceptions.NotImplementedException;

import Triping.utils.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.*;

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
    public ResponseEntity<?> addContributor(@PathVariable Long id, @PathVariable String username) throws ResourceNotFoundException {
        final User authenticatedUser = this.getAuthenticatedUser();

        final Trip trip = tripService.getOne(id,authenticatedUser);
        final User invitee = userService.findUserByUsername(username);
        if(trip.isOwner(authenticatedUser)){
            tripService.addContributorToTrip(trip, invitee);
            return new ResponseEntity<>("User invitation is pending on confirmation", HttpStatus.OK);
        }
        else{
            throw new AccessDeniedException("No tienes acceso para realizar esta operacion");
        }
    }

    @DeleteMapping("/{id}/party/{username}")
    public ResponseEntity<?> removeContributor(@PathVariable Long id, @PathVariable String username) throws ResourceNotFoundException {
        final User authenticatedUser = this.getAuthenticatedUser();

        final Trip trip = tripService.getOne(id,authenticatedUser);
        final User contributor = userService.findUserByUsername(username);
        if(trip.isOwner(authenticatedUser)){
            tripService.removeContributorFromTrip(trip, contributor);
            return new ResponseEntity<>("Contributor removed from trip", HttpStatus.OK);
        }
        else{
            throw new AccessDeniedException("No tienes acceso para realizar esta operacion");
        }
    }

    @PostMapping(path="/{id}/invite")
    public ResponseEntity<?> generateInvitationLink(@PathVariable Long id) throws ResourceNotFoundException {
        final User authenticatedUser = this.getAuthenticatedUser();
        final Trip trip = tripService.getOne(id, authenticatedUser);

        String token = UUID.randomUUID().toString();
        tripService.createInvitationToken(trip, token);

        String confirmationUrl = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString() + "/join?token=" + token;
        return ResponseEntity.ok(confirmationUrl);
    }

    @GetMapping(path="/join")
    public ResponseEntity<?> joinTripViaInvitationLink(@RequestParam String token){
        InvitationToken invitationToken = tripService.getInvitationToken(token);

        if (invitationToken == null) {
            return new ResponseEntity<>("Invalid token", HttpStatus.BAD_REQUEST);
        }
        if(invitationToken.isExpired()){
            return new ResponseEntity<>("Expired token", HttpStatus.BAD_REQUEST);
        }

        Trip trip = invitationToken.getTrip();
        final User invitee = this.getAuthenticatedUser();
        tripService.addContributorToTrip(trip, invitee);

        URI resourceLocation = ServletUriComponentsBuilder.fromPath("/api/v1/trips/{id}")
                .buildAndExpand(trip.getTripId()).toUri();

        return ResponseEntity.ok(resourceLocation);
    }

    public User getAuthenticatedUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userService.findUserByUsername(auth.getPrincipal().toString());
    }
}
