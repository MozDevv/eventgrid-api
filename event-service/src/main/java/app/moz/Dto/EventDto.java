package app.moz.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventDto {
    private int eventId;

    private String eventName;

    private String eventDate;

    private String eventVenue;

    private Boolean isAvailable;
}
