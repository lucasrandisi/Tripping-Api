package Triping.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter @Setter
public class Interest {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String description;

    @ManyToMany(mappedBy = "userInterests")
    @JsonIgnore
    private Set<User> users;

    @ManyToMany(mappedBy = "markerInterests")
    @JsonIgnore
    private Set<Marker> markers;

}
