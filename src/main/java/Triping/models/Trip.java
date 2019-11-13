package Triping.models;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import Triping.utils.exceptions.NotImplementedException;

@Entity
public class Trip {

    @Id @GeneratedValue
    private Long tripId;

    private String title;
    private String description;
    private Date departureDate;
    private Date endDate;

    public enum accessType {PUBLIC, PRIVATE}
    private accessType accessibility;

    @ManyToMany
    @JoinTable(name = "trips_itineraries", joinColumns = @JoinColumn(name = "itinerary_id"), inverseJoinColumns = @JoinColumn(name = "trip_id"))
    private List<Itinerary> itineraries;

    @OneToMany(mappedBy = "trip")
    private Set<TripParty> contributingUsers;

    @ManyToOne
    @JoinColumn(name="owner_id")
    private User owner;

    public Trip(){
        this.accessibility = accessType.PUBLIC;
    }

    //ToDo
    public float calculateTripCost(){
        throw new NotImplementedException();
    }

    public Long getTripId() { return tripId; }

    public void setTripId(Long tripId) { this.tripId = tripId;}

    public accessType getAccessibility() { return accessibility; }

    public void setAccessibility(accessType accessibility) { this.accessibility = accessibility; }

    public Date getDepartureDate() { return departureDate; }

    public void setDepartureDate(Date departureDate) { this.departureDate = departureDate; }

    public Date getEndDate() { return endDate; }

    public void setEndDate(Date endDate) { this.endDate = endDate; }

    public List<Itinerary> getItineraries() { return itineraries; }

    public void setItineraries(List<Itinerary> itineraries) { this.itineraries = itineraries; }

    public Set<TripParty> getContributingUsers() { return contributingUsers; }

    public void setContributingUsers(Set<TripParty> contributingUsers) { this.contributingUsers = contributingUsers; }

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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User user) {
        this.owner = user;
    }
}
