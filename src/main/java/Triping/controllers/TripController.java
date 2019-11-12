package Triping.controllers;

import Triping.models.Trip;
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
        Trip trip = tripService.getOne(id);
        return ResponseEntity.ok().body(trip);
    }

    @PostMapping
    public Trip createNewTrip(@Valid @RequestBody Trip trip){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findUserByUsername(auth.getPrincipal().toString());
        trip.setOwner(currentUser);
        return tripService.createNewTrip(trip);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Trip> updateTrip(@PathVariable(value = "id") Long id, @Valid @RequestBody Trip tripDetails) throws ResourceNotFoundException {
        final Trip updatedTrip = tripService.updateTrip(id, tripDetails);
        return ResponseEntity.ok().body(updatedTrip);
    }

    @DeleteMapping("{id}")
    public Map<String, Boolean> deleteTrip(@PathVariable Long id) throws ResourceNotFoundException {

        tripService.deleteTrip(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    @PostMapping(path="/trip/{id}/invite")
    public GenericResponse inviteFriendsToTrip(){
        throw new NotImplementedException();
    }

}
