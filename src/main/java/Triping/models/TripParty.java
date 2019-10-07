package Triping.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class TripParty implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn
    private User user;

    @Id
    @ManyToOne
    @JoinColumn
    private Trip trip;

    enum PartyPermission {EDIT, COMMENT, SEE }
    private PartyPermission role;
    private boolean invitationConfirmationPending;

    public TripParty(){
        this.invitationConfirmationPending = true;
    }

    public PartyPermission getRole() { return role;}

    public void setRole(PartyPermission role) { this.role = role; }

    public boolean isPendingConfirmationInvitation() { return invitationConfirmationPending;}

    public void setPendingConfirmationInvitation(boolean invitationConfirmationPending) { this.invitationConfirmationPending = invitationConfirmationPending;}
}
