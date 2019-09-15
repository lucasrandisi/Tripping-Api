package Triping.models;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Itinerary {

    @Id
    private Long id;

    @ManyToMany(mappedBy = "trips_itineraries")
    private List<Trip> trips = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "itineraries_markers", joinColumns = @JoinColumn(name = "marker_id"), inverseJoinColumns = @JoinColumn(name = "itinerary_id"))
    private ArrayList<Marker> markers = new ArrayList<>();

    //ToDo
    public float calculateItineraryCost(){
        throw new NotImplementedException();
    }
}
