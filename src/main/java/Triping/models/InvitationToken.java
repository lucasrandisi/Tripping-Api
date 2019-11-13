package Triping.models;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@Entity
public class InvitationToken {

    private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(targetEntity = Trip.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "trip_id", foreignKey = @ForeignKey(name = "FK_JOIN_TRIP"))
    private Trip trip;

    private String token;
    private Date expiryDate;

    public enum status {
        INVALID, EXPIRED, VALID
    }

    public InvitationToken() {
        super();
    }

    public InvitationToken(final String token) {
        super();

        this.token = token;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }

    public InvitationToken(final Trip trip, final String token) {
        this(token);
        this.trip = trip;
    }

    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

    public boolean isExpired() {
        Calendar cal = Calendar.getInstance();
        return (expiryDate.getTime() - cal.getTime().getTime()) <= 0;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public void updateToken(final String token) {
        this.token = token;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((expiryDate == null) ? 0 : expiryDate.hashCode());
        result = prime * result + ((token == null) ? 0 : token.hashCode());
        result = prime * result + ((trip == null) ? 0 : trip.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InvitationToken)) return false;
        InvitationToken that = (InvitationToken) o;
        return id.equals(that.id) &&
                trip.equals(that.trip) &&
                token.equals(that.token);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("InvitationToken [String=").append(token).append("]").append("[Expires").append(expiryDate).append("]");
        return builder.toString();
    }
}
