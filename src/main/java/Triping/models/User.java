package Triping.models;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @NaturalId
    @Column(unique = true)
    private String username;

    private Boolean enabled;
    private String password;
    private String email;
    private byte[] userImage;
    private String salt;

    @OneToMany
    private List<User> friends;

    @OneToMany(mappedBy = "user")
    private List<Trip> ownedTrips;

    @OneToMany
    private List<Itinerary> ownedItineraries;

    @OneToMany
    private List<Marker> customMarkers;

    @ManyToMany
    @JoinTable(name = "users_interests", joinColumns = @JoinColumn(name = "interest_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<Interest> userInterests;

    @OneToMany(mappedBy = "user")
    private Set<TripParty> tripParties;

    @ManyToMany
    @JoinTable(name = "saved_markers", joinColumns = @JoinColumn(name = "marker_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<Marker> savedMarkers;

    @ManyToMany
    @JoinTable(name = "saved_trips", joinColumns = @JoinColumn(name = "trip_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<Marker> savedTrips;

    public User(){
        this.enabled = false;
    }


    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() { return salt; }

    public void setSalt(String salt) { this.salt = salt; }

    public byte[] getUserImage() { return userImage; }

    public void setUserImage(byte[] userImage) { this.userImage = userImage; }

    public String getEmail() {return email; }

    public void setEmail(String email) {this.email = email;}

    public boolean isEnabled() { return enabled; }

    public void setEnabled(boolean enabled) { this.enabled = enabled; }

    public List<User> getFriends() { return friends; }

    public void setFriends(List<User> friends) { this.friends = friends;}
}
