package Triping.services;

import Triping.models.Trip;
import Triping.utils.exceptions.ResourceNotFoundException;

import java.util.List;

public interface ITripService {
    
    List<Trip> findAll();

    Trip getOne(Long id) throws ResourceNotFoundException;

    Trip createNewTrip(Trip trip);

    Trip updateTrip(Long id, Trip tripDetails) throws ResourceNotFoundException;

    void deleteTrip(Long id) throws ResourceNotFoundException;
}
