package Triping.repositories;

import Triping.models.Trip;
import Triping.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TripRepository extends JpaRepository<Trip, Long> {
    List<Trip> findByOwnerAndAccessibility(User userToFind, Trip.accessType accessType);

    List<Trip> findByOwnerAndAccessibilityAndTitleContaining(User userToFind, Trip.accessType accessType, String title);

    Optional<Trip> findByTripIdAndAccessibility(long parsedTripID, Trip.accessType accessType);
}
