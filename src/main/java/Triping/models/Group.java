package Triping.models;

import java.sql.Date;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Size;

import Triping.utils.exceptions.NotImplementedException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="\"group\"")
@Getter @Setter
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupId;

    @Size(max = 20)
    private String title;

    @Size(max = 500)
    private String description;
    private Date creationDate;

    public enum accessType {PUBLIC, PRIVATE}
    private accessType accessibility;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Post> posts;

    @ManyToOne
    @JoinColumn(name="owner_id", referencedColumnName = "userId")
    @JsonIgnore
    private User owner;

    public Group(){
        this.accessibility = accessType.PUBLIC;
    }

    public Boolean hasOwner(User user){
        return this.getOwner().equals(user);
    }

}
