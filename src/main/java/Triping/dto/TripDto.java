package Triping.dto;

import Triping.models.Itinerary;
import Triping.models.Trip;
import Triping.models.TripParty;
import Triping.models.User;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.List;
import java.util.Set;

@Getter @Setter
public class TripDto {
    private Long id;
    private String title;
    private String description;
    private Date departureDate;
    private Date endDate;

    private UserDto owner;

    private List<Itinerary> itineraries;
    private Set<TripParty> contributingUsers;

    public TripDto(Trip trip) {
        this.setId(trip.getTripId());
        this.setTitle(trip.getTitle());
        this.setDescription(trip.getDescription());
        this.setDepartureDate(trip.getDepartureDate());
        this.setEndDate(trip.getEndDate());

        UserDto owner = new UserDto(trip.getOwner());
        this.setOwner(owner);
        this.setItineraries(trip.getItineraries());
        this.setContributingUsers(trip.getContributingUsers());
    }
}
