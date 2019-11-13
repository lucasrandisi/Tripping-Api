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
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Trip {

    @Id @GeneratedValue
    private Long tripId;

    private String title;
    private String description;
    private Boolean accessibility;
    private Date departureDate;
    private Date endDate;

    @ManyToMany
    @JoinTable(name = "trips_itineraries", joinColumns = @JoinColumn(name = "itinerary_id"), inverseJoinColumns = @JoinColumn(name = "trip_id"))
    private List<Itinerary> itineraries;

    @OneToMany(mappedBy = "trip")
    private Set<TripParty> contributingUsers;

    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName = "userId")
    @JsonIgnoreProperties("ownedTrips")
    private User owner;

    public Trip(){
        this.accessibility = true;
    }

    //ToDo
    public float calculateTripCost(){
        throw new NotImplementedException();
    }

    public Long getId() { return tripId; }

    public void setId(Long id) { this.tripId = id;}

    public boolean isAccessibility() { return accessibility; }

    public void setAccessibility(boolean accessibility) { this.accessibility = accessibility; }

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

    public Boolean getAccessibility() {
        return accessibility;
    }

    public void setAccessibility(Boolean accessibility) {
        this.accessibility = accessibility;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User user) {
        this.owner = user;
    }


}
