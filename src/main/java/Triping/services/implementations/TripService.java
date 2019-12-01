package Triping.services.implementations;

import Triping.models.InvitationToken;
import Triping.models.Trip;
import Triping.models.TripParty;
import Triping.models.User;
import Triping.repositories.*;
import Triping.services.specifications.IAccountService;
import Triping.services.specifications.ITripService;
import Triping.utils.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Transactional
public class TripService implements ITripService {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private TripPartyRepository tripPartyRepository;

    @Autowired
    private InvitationTokenRepository invitationTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IAccountService accountService;

    @Override
    public List<Trip> findAll() {
        return tripRepository.findAll();
    }

    @Override
    public Trip getOne(Long id) throws ResourceNotFoundException{
        final Trip trip = tripRepository.getOne(id);

        if(!canUserSeeContent(trip)){
            throw new ResourceNotFoundException("No se encontro el viaje.");
        }
        return trip;
    }

    @Override
    public Trip createNewTrip(Trip trip){
        final User authenticatedUser = userRepository.findByUsername(accountService.currentAuthenticatedUser());
        trip.setOwner(authenticatedUser);
        return tripRepository.save(trip);
    }

    @Override
    public Trip updateTrip(Long id, Trip tripDetails)  throws ResourceNotFoundException{
        final Trip edit = this.getOne(id);
        final User authenticatedUser = userRepository.findByUsername(accountService.currentAuthenticatedUser());

        if(edit.getOwner().equals(authenticatedUser)) {
            edit.setTitle(tripDetails.getTitle());
            edit.setDescription(tripDetails.getDescription());
            return tripRepository.save(edit);
        }
        else{
            throw new ResourceNotFoundException("No tienes acceso para realizar esta operacion");
        }
    }

    @Override
    public void deleteTrip(Long id)  throws ResourceNotFoundException{
        final Trip trip = this.getOne(id);
        final User authenticatedUser = userRepository.findByUsername(accountService.currentAuthenticatedUser());

        if(trip.hasOwner(authenticatedUser)) {
            tripRepository.delete(trip);
        }
        else{
            throw new ResourceNotFoundException("No tienes acceso para realizar esta operacion");
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

    @Override
    public InvitationToken getInvitationToken(String token) {
        return invitationTokenRepository.findByToken(token);
    }

    @Override
    public void createInvitationToken(Trip trip, String token) {
        InvitationToken verificationToken = new InvitationToken(trip, token);
        invitationTokenRepository.save(verificationToken);
    }


    public boolean canUserSeeContent(Trip trip){
        final User authenticatedUser = userRepository.findByUsername(accountService.currentAuthenticatedUser());

        if(trip.getAccessibility() == Trip.accessType.PUBLIC){
            return true;
        }
        if(trip.hasOwner(authenticatedUser)){
            return true;
        }
        TripParty party = tripPartyRepository.canUserSeeContent(trip, authenticatedUser);
        if(party.isInvitationConfirmationPending()) {
            return false;
        }
        if(party.getRole() == TripParty.PartyPermission.SEE){
            return true;
        }
        return false;
    }

}
