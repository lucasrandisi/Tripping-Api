package Triping.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Getter @Setter
public class Marker {
    /* Point of interest*/

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long markerId;

    private String description;
    private String address;

    @Column(name = "lat", nullable = false)
    private Double latitude;

    @Column(name = "lng", nullable = false)
    private Double longitude;

    @ManyToMany
    @JoinTable(name = "markers_interests", joinColumns = @JoinColumn(name = "interest_id"), inverseJoinColumns = @JoinColumn(name = "marker_id"))
    private Set<Interest> markerInterests;

    @OneToMany(mappedBy = "marker")
    private List<ItineraryMarkers> itineraries;

    //Esta propiedad se usa en caso de que sea un marker personalizado
    @ManyToOne
    @JoinColumn(name="owner")
    @JsonIgnore
    private User owner;
}
