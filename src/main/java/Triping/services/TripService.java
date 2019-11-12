package Triping.services;

import Triping.models.Trip;
import Triping.repositories.TripRepository;
import Triping.utils.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TripService implements ITripService{

    @Autowired
    private TripRepository tripRepository;

    @Override
    public List<Trip> findAll() {
        return tripRepository.findAll();
    }

    @Override
    public Trip getOne(Long id) throws ResourceNotFoundException {
        final Trip trip = tripRepository.getOne(id);
        if(trip == null){
            throw new ResourceNotFoundException("No se encontro el viaje.");
        }
        return trip;
    }

    @Override
    public Trip createNewTrip(Trip trip) {
        return tripRepository.save(trip);
    }

    @Override
    public Trip updateTrip(Long id, Trip tripDetails) throws ResourceNotFoundException {
        final Trip edit = this.getOne(id);

        edit.setTitle(tripDetails.getTitle());
        edit.setDescription(tripDetails.getTitle());
        return tripRepository.save(edit);
    }

    @Override
    public void deleteTrip(Long id) throws ResourceNotFoundException {
        final Trip trip = this.getOne(id);
        tripRepository.delete(trip);
    }
}
