package Triping.repositories;

import Triping.models.Trip;
import Triping.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TripRepository extends JpaRepository<Trip, Long> {
    List<Trip> findByOwnerAndAccessibility(User userToFind, Boolean accessibility);

    List<Trip> findByOwnerAndAccessibilityAndTitleContaining(User userToFind, boolean accessibility, String title);
}
