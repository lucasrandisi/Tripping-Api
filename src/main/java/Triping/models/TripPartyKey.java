package Triping.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter @Setter
public class TripPartyKey implements Serializable {

    @Column(name = "trip_id")
    Long tripId;

    @Column(name = "user_id")
    Long userId;

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