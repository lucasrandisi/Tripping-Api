package Triping.models;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import Triping.utils.exceptions.NotImplementedException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
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
    @JsonIgnore
    private List<Itinerary> itineraries;

    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<TripParty> contributingUsers;

    @ManyToOne
    @JoinColumn(name="owner_id", referencedColumnName = "userId")
    @JsonIgnore
    private User owner;

    public Trip(){
        this.accessibility = accessType.PUBLIC;
    }

    //ToDo
    public float calculateTripCost(){
        throw new NotImplementedException();
    }

    public Boolean isOwner(User user){
        return this.getOwner().equals(user);
    }

}
