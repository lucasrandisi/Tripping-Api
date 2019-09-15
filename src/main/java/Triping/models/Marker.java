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
    private float latitude;

    @Column(name = "lng", nullable = false)
    private float longitude;

    @ManyToMany
    @JoinTable(name = "markers_interests", joinColumns = @JoinColumn(name = "interest_id"), inverseJoinColumns = @JoinColumn(name = "marker_id"))
    private Set<Interest> relatedInterests;

    @ManyToMany(mappedBy = "itineraries_markers")
    private List<Itinerary> itineraries;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    public float getLatitude() { return latitude; }

    public void setLatitude(float latitude) { this.latitude = latitude; }

    public float getLongitude() { return longitude; }

    public void setLongitude(float longitude) { this.longitude = longitude; }

    public Set<Interest> getRelatedInterests() { return relatedInterests; }

    public void setRelatedInterests(Set<Interest> relatedInterests) { this.relatedInterests = relatedInterests; }
}
