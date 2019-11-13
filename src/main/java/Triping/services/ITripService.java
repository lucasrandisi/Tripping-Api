package Triping.services;

import Triping.models.InvitationToken;
import Triping.models.Trip;
import Triping.models.TripParty;
import Triping.models.User;
import Triping.utils.exceptions.ResourceNotFoundException;

import java.util.List;

public interface ITripService {
    
    List<Trip> findAll();

    Trip getOne(Long id, User authenticatedUser) throws ResourceNotFoundException;

    Trip createNewTrip(Trip trip, User authenticatedUser);

    Trip updateTrip(Long id, Trip tripDetails, User authenticatedUser) throws ResourceNotFoundException;

    void deleteTrip(Long id, User authenticatedUser) throws ResourceNotFoundException;

    void addContributorToTrip(Trip trip, User contributor);

    void removeContributorFromTrip(Trip trip, User contributor);

    InvitationToken getInvitationToken(String token);

    void createInvitationToken(Trip trip, String token);
}
