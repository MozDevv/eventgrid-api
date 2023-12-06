package app.moz.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventRequest {

    private String eventName;

    private String eventDate;

    private String eventVenue;

    private Boolean isAvailable;
}
