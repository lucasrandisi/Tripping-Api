package Triping.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ItineraryMarkersKey implements Serializable {

    @Column(name = "itinerary_id")
    private Long itineraryId;

    @Column(name = "marker_id")
    private Long markerId;

    public Long getItineraryId() { return itineraryId; }

    public void setItineraryId(Long itineraryId) { this.itineraryId = itineraryId; }

    public Long getMarkerId() { return markerId; }

    public void setMarkerId(Long markerId) { this.markerId = markerId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItineraryMarkersKey that = (ItineraryMarkersKey) o;
        return Objects.equals(itineraryId, that.itineraryId) &&
                Objects.equals(markerId, that.markerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itineraryId, markerId);
    }
}
