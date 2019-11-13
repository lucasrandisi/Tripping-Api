package Triping.services;

import Triping.models.Trip;
import Triping.models.TripParty;
import Triping.models.User;
import Triping.repositories.TripPartyRepository;
import Triping.repositories.TripRepository;
import Triping.repositories.UserRepository;
import Triping.utils.exceptions.AccessDeniedException;
import Triping.utils.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Transactional
public class TripService implements ITripService{

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private TripPartyRepository tripPartyRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Trip> findAll() {
        return tripRepository.findAll();
    }

    @Override
    public Trip getOne(Long id, User authenticatedUser) throws ResourceNotFoundException {
        Trip trip;
        try {
            trip = tripRepository.getOne(id);
        }
        catch(EntityNotFoundException e){
            throw new ResourceNotFoundException("No se encontro el viaje.");
        }
        if(canUserSeeContent(trip, authenticatedUser)) {
            return trip;
        }
        else{
            throw new AccessDeniedException("No tienes acceso para realizar esta operacion");
        }
    }

    @Override
    public Trip createNewTrip(Trip trip, User authenticatedUser) {
        return tripRepository.save(trip);
    }

    @Override
    public Trip updateTrip(Long id, Trip tripDetails, User authenticatedUser) throws ResourceNotFoundException {
        final Trip edit = this.getOne(id, authenticatedUser);

        if(edit.getOwner().equals(authenticatedUser)) {
            edit.setTitle(tripDetails.getTitle());
            edit.setDescription(tripDetails.getDescription());
            return tripRepository.save(edit);
        }
        else{
            throw new AccessDeniedException("No tienes acceso para realizar esta operacion");
        }
    }

    @Override
    public void deleteTrip(Long id, User authenticatedUser) throws ResourceNotFoundException {
        final Trip trip = this.getOne(id, authenticatedUser);
        if(trip.getOwner().equals(authenticatedUser)) {
            tripRepository.delete(trip);
        }
        else{
            throw new AccessDeniedException("No tienes acceso para realizar esta operacion");
        }
    }

    @Override
    public void addContributorToTrip(Trip trip, final User invitee) {
        TripParty party = new TripParty(trip, invitee, TripParty.PartyPermission.SEE);
        tripPartyRepository.save(party);
    }

    @Override
    public void removeContributorFromTrip(Trip trip, User contributor) {
        tripPartyRepository.deleteByTripAndUser(trip,contributor);
    }


    public boolean canUserSeeContent(Trip trip, User user){
        if(trip.getAccessibility() == Trip.accessType.PUBLIC){
            return true;
        }
        if(trip.getOwner().equals(user)){
            return true;
        }
        TripParty party = tripPartyRepository.canUserSeeContent(trip, user);
        if(party.isInvitationConfirmationPending()) {
            return false;
        }
        if(party.getRole() == TripParty.PartyPermission.SEE){
            return true;
        }
        return false;
    }

}
