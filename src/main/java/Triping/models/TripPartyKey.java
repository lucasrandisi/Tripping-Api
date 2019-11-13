package Triping.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public
class TripPartyKey implements Serializable {

    @Column(name = "trip_id")
    Long tripId;

    @Column(name = "user_id")
    Long userId;

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public TripPartyKey(Long tripId, Long userId) {
        this.tripId = tripId;
        this.userId = userId;
    }

    public TripPartyKey(){};

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TripPartyKey)) return false;
        TripPartyKey that = (TripPartyKey) o;
        return Objects.equals(tripId, that.tripId) &&
                Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tripId, userId);
    }
}