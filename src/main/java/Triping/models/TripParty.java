package Triping.models;

import javax.persistence.*;

@Entity
public class TripParty {

    @EmbeddedId
    private TripPartyKey id;

    @ManyToOne
    @MapsId("trip_id")
    @JoinColumn(name = "trip_id")
    private User user;

    @ManyToOne
    @MapsId("trip_id")
    @JoinColumn(name = "trip_id")
    private Trip trip;

    enum PartyPermission {EDIT, COMMENT, SEE };
    private PartyPermission role;

    public TripPartyKey getId() { return id; }

    public void setId(TripPartyKey id) { this.id = id; }

    public PartyPermission getRole() { return role;}

    public void setRole(PartyPermission role) { this.role = role; }
}
