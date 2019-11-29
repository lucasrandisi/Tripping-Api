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
    @EmbeddedId
    private ItineraryMarkerKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("marker_id")
    @JoinColumn(name = "marker_id")
    private Marker marker;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("itinerary_id")
    @JoinColumn(name = "itinerary_id")
    private Itinerary itinerary;

    private Time startTime;

}
