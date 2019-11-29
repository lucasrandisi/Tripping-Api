package Triping.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter @Setter
public class Interest {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String description;

    @ManyToMany(mappedBy = "userInterests")
    private Set<User> users;

    @ManyToMany(mappedBy = "markerInterests")
    private Set<Marker> markers;

}
