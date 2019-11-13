package Triping.dto;

import Triping.models.Itinerary;
import Triping.models.TripParty;
import Triping.models.User;

import java.sql.Date;
import java.util.List;
import java.util.Set;

public class TripDto {
    private Long id;
    private String title;
    private String description;
    private Date departureDate;
    private Date endDate;

    private UserDto owner;

    private List<Itinerary> itineraries;
    private Set<TripParty> contributingUsers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public UserDto getOwner() {
        return owner;
    }

    public void setOwner(UserDto owner) {
        this.owner = owner;
    }

    public List<Itinerary> getItineraries() {
        return itineraries;
    }

    public void setItineraries(List<Itinerary> itineraries) {
        this.itineraries = itineraries;
    }

    public Set<TripParty> getContributingUsers() {
        return contributingUsers;
    }

    public void setContributingUsers(Set<TripParty> contributingUsers) {
        this.contributingUsers = contributingUsers;
    }
}
