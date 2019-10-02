package Triping.models;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Itinerary {

    @Id
    private Long id;

    @ManyToMany(mappedBy = "itineraries")
    private List<Trip> trips = new ArrayList<>();

    @OneToMany(mappedBy = "itinerary")
    private List<ItineraryMarkers> markers = new ArrayList<>();

    //ToDo
    public float calculateItineraryCost(){
        throw new NotImplementedException();
    }
}
