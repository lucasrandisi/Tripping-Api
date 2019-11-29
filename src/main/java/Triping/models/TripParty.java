package Triping.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter @Setter
public class TripParty implements Serializable {

    @EmbeddedId
    private TripPartyKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("user_id")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("trip_id")
    @JoinColumn(name = "trip_id")
    private Trip trip;

    public enum PartyPermission {EDIT, COMMENT, SEE }

    @Enumerated(EnumType.STRING)
    private PartyPermission role;

    private boolean invitationConfirmationPending;

    public TripParty(Trip trip, User user, PartyPermission role){
        this.id = new TripPartyKey(trip.getTripId(),user.getUserId());
        this.user = user;
        this.trip = trip;
        this.invitationConfirmationPending = true;
    }
    public TripParty(){
        this.invitationConfirmationPending = true;
    }
}
