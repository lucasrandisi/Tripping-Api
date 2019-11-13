package Triping.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class TripParty implements Serializable {

    //Clave de entidad compuesta necesita una clase para la clave
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

    public TripParty(Trip trip, User user, PartyPermission role){
        this.id = new TripPartyKey(trip.getTripId(),user.getUserId());
        this.user = user;
        this.trip = trip;
        this.invitationConfirmationPending = true;
    }

    private boolean invitationConfirmationPending;

    public TripParty(){
        this.invitationConfirmationPending = true;
    }

    public PartyPermission getRole() { return role;}

    public void setRole(PartyPermission role) { this.role = role; }

    public boolean isPendingConfirmationInvitation() { return invitationConfirmationPending;}

    public void setPendingConfirmationInvitation(boolean invitationConfirmationPending) { this.invitationConfirmationPending = invitationConfirmationPending;}

    public TripPartyKey getId() { return id; }

    public void setId(TripPartyKey id) { this.id = id; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public Trip getTrip() { return trip; }

    public void setTrip(Trip trip) { this.trip = trip; }

    public boolean isInvitationConfirmationPending() { return invitationConfirmationPending; }

    public void setInvitationConfirmationPending(boolean invitationConfirmationPending) { this.invitationConfirmationPending = invitationConfirmationPending; }
}
