package Triping.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter @Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NaturalId
    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;

    private String password;
    private Boolean enabled;
    private byte[] userImage;
    private String name;
    private String surname;

    @ManyToMany
    @JoinTable(name = "user_friends", joinColumns = @JoinColumn(name="user_id"), inverseJoinColumns=@JoinColumn(name="friend_id"))
    @JsonBackReference
    private List<User> friends;

    @ManyToMany(mappedBy = "friends")
    private List<User> friendOf;

    @OneToMany(mappedBy = "owner")
    @JsonIgnoreProperties("owner")
    private List<Trip> ownedTrips;

    @OneToMany(mappedBy="owner", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Itinerary> ownedItineraries;

    @OneToMany(mappedBy = "owner", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Marker> customMarkers;

    @ManyToMany()
    @JoinTable(name = "users_interests", joinColumns = @JoinColumn(name = "interest_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    @JsonBackReference
    private Set<Interest> userInterests;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return userId.equals(user.userId) &&
                username.equals(user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, username);
    }
}
