package Triping.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
/*
JPA composite key for TripParty entity
 */
@Embeddable
public class TripPartyKey implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "trip_id")
    private Long tripId;

    public Long getUserId() { return userId; }

    public void setUserId(Long userId) { this.userId = userId; }

    public Long getTripId() { return tripId; }

    public void setTripId(Long tripId) { this.tripId = tripId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TripPartyKey that = (TripPartyKey) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(tripId, that.tripId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, tripId);
    }
}
