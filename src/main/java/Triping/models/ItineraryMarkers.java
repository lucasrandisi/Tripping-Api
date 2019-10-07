package Triping.models;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;

@Entity
public class ItineraryMarkers implements Serializable {

    //ToDo: Implementar comentarios de usuarios
    @Id
    @ManyToOne
    @JoinColumn
    private Itinerary itinerary;

    @Id
    @ManyToOne
    @JoinColumn
    private Marker marker;

    private Time startTime;

    public Time getStartTime() { return startTime; }

    public void setStartTime(Time startTime) { this.startTime = startTime; }
}
