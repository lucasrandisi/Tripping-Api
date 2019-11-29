package Triping.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;

@Entity
@Getter @Setter
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

}
