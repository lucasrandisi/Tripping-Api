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
    private Set<User> following;

    @ManyToMany(mappedBy = "following")
    private Set<User> followers;

    @OneToMany(mappedBy = "owner")
    @JsonIgnoreProperties("owner")
    private List<Group> ownedGroups;

    @OneToMany(mappedBy = "creator")
    @JsonIgnoreProperties("creator")
    private List<Post> createdPosts;

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

    public boolean follow(User user) {
        if(!user.equals(this))
        return following.add(user);
        else return false;
    }

    public boolean unFollow(User user) {
        return following.remove(user);
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
