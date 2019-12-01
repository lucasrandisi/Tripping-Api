package Triping.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter @Setter
public class User implements UserDetails {
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

    @Lob
    private byte[] profilePicture;

    private String name;
    private String surname;

    @ManyToMany
    @JoinTable(name = "followers", joinColumns = @JoinColumn(name="user"), inverseJoinColumns=@JoinColumn(name="followed_user"))
    @JsonBackReference
    private List<User> following;

    @ManyToMany(mappedBy = "following")
    private List<User> followers;

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return Collections.emptyList();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    public User(){
        this.enabled = false;
    }

    public boolean doesFollow(User user) {
        return following.contains(user);
    }

    public void follow(User user) {
        if(!user.equals(this))
        following.add(user);
    }

    public void unFollow(User user) {
        following.remove(user);
    }

    public void removeFollower(User user) {
        followers.remove(this);
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
