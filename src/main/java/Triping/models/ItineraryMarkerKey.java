package Triping.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter @Setter
public class ItineraryMarkerKey implements Serializable {

    @Column(name = "marker_id")
    Long markerId;

    @Column(name = "itinerary_id")
    Long itineraryId;

    public ItineraryMarkerKey(Long markerId, Long userId) {
        this.markerId = markerId;
        this.itineraryId = itineraryId;
    }

    public ItineraryMarkerKey() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItineraryMarkerKey)) return false;
        ItineraryMarkerKey that = (ItineraryMarkerKey) o;
        return Objects.equals(markerId, that.markerId) &&
                Objects.equals(itineraryId, that.itineraryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(markerId, itineraryId);
    }
}
