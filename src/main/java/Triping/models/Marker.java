package Triping.models;

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
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

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

}
