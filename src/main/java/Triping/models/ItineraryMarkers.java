package Triping.models;

import javax.persistence.*;
import java.sql.Time;

@Entity
public class ItineraryMarkers {

    @EmbeddedId
    private ItineraryMarkersKey id;

    //ToDo: Implementar comentarios de usuarios

    @ManyToOne
    @MapsId("itinerary_id")
    @JoinColumn(name = "itinerary_id")
    private Itinerary itinerary;

    @ManyToOne
    @MapsId("marker_id")
    @JoinColumn(name = "marker_id")
    private Marker marker;

    private Time startTime;

    public ItineraryMarkersKey getId() { return id; }

    public void setId(ItineraryMarkersKey id) { this.id = id; }

    public Time getStartTime() { return startTime; }

    public void setStartTime(Time startTime) { this.startTime = startTime; }
}
