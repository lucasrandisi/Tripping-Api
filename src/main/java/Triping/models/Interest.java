package Triping.models;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Interest {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    private String description;

    @ManyToMany(mappedBy = "userInterests")
    private Set<User> users;

    @ManyToMany(mappedBy = "markerInterests")
    private Set<Marker> markers;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getDescription() { return description;}

    public void setDescription(String description) { this.description = description; }

    public Set<User> getUsers() { return users; }

    public void setUsers(Set<User> users) { this.users = users; }
}
