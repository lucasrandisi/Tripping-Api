package Triping.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Date;

@Entity
@Getter @Setter
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Size(max = 20)
    private String title;

    @Size(max = 500)
    private String content;

    private Date creationDate;

    @ManyToOne
    @JoinColumn(name="creator_id", referencedColumnName = "userId")
    @JsonIgnore
    private User creator;

    @ManyToOne
    @JoinColumn(name="group_id", referencedColumnName = "groupId")
    private Group parent;
}
