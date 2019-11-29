package Triping.models;

import Triping.utils.exceptions.NotImplementedException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Itinerary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itineraryId;

    @ManyToMany(mappedBy = "itineraries")
    private List<Trip> trips = new ArrayList<>();

    @OneToMany(mappedBy = "itinerary")
    private List<ItineraryMarkers> markers = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name="owner")
    @JsonIgnore
    private User owner;

    //ToDo
    public float calculateItineraryCost(){
        throw new NotImplementedException();
    }
}
