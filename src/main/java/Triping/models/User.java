package Triping.models;

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
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long userId;

    @NaturalId
    @Column(unique = true)
    private String username;

    private String password;
    private Boolean enabled;
    private String email;
    private byte[] userImage;
    private String name;
    private String surname;

    @ManyToMany
    @JoinTable(name = "user_friends",
            joinColumns = @JoinColumn(name="userID", referencedColumnName = "userId"),
            inverseJoinColumns=@JoinColumn(name="friendID", referencedColumnName = "userId"))
    @JsonIgnoreProperties({"friends", "friendOF"})
    private List<User> friends;


    @ManyToMany
    @JoinTable(name="user_friends",
            joinColumns=@JoinColumn(name="friendID", referencedColumnName = "userId"),
            inverseJoinColumns=@JoinColumn(name="userID", referencedColumnName = "userId"))
    @JsonIgnoreProperties({"friends", "friendOF"})
    private List<User> friendOf;


    @OneToMany(mappedBy = "owner")
    @JsonIgnoreProperties("owner")
    private List<Trip> ownedTrips;

    @OneToMany
    private List<Itinerary> ownedItineraries;

    @OneToMany
    private List<Marker> customMarkers;

    @ManyToMany()
    @JoinTable(name = "users_interests", joinColumns = @JoinColumn(name = "interest_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
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
