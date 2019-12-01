package Triping.services.specifications;

import Triping.models.InvitationToken;
import Triping.models.Trip;
import Triping.models.User;
import Triping.utils.exceptions.ResourceNotFoundException;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public interface ITripService {
    
    List<Trip> findAll();

    Trip getOne(Long id) throws ResourceNotFoundException;

    Trip createNewTrip(Trip trip);

    Trip updateTrip(Long id, Trip tripDetails)  throws ResourceNotFoundException;

    void deleteTrip(Long id)  throws ResourceNotFoundException;

    void addContributorToTrip(Trip trip, User contributor);

    void removeContributorFromTrip(Trip trip, User contributor);

    InvitationToken getInvitationToken(String token);

    void createInvitationToken(Trip trip, String token);
}
