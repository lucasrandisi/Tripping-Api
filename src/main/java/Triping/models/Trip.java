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
    private Long id;

    private Boolean accessibility;
    private Date departureDate;
    private Date endDate;

    @ManyToMany
    @JoinTable(name = "trips_itineraries", joinColumns = @JoinColumn(name = "itinerary_id"), inverseJoinColumns = @JoinColumn(name = "trip_id"))
    private List<Itinerary> itineraries;

    @OneToMany(mappedBy = "trip")
    private Set<TripParty> contributingUsers;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    public Trip(){
        this.accessibility = true;
    }

    //ToDo
    public float calculateTripCost(){
        throw new NotImplementedException();
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id;}

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
}
