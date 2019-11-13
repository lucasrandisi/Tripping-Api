package Triping.repositories;

import Triping.models.Trip;
import Triping.models.TripParty;
import Triping.models.TripPartyKey;
import Triping.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TripPartyRepository extends JpaRepository<TripParty, TripPartyKey> {


    @Query("select p from TripParty p where (p.user = ?2  and p.trip = ?1)")
    TripParty canUserSeeContent(Trip trip, User user);

}
