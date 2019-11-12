package Triping.models;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
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

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    public Double getLatitude() { return latitude; }

    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }

    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public Set<Interest> getRelatedInterests() { return markerInterests; }

    public void setRelatedInterests(Set<Interest> markerInterests) { this.markerInterests = markerInterests; }
}
