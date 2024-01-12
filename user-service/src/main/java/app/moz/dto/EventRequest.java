package app.moz.dto;

import jdk.jfr.DataAmount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EventRequest {

    private int id;

    private String title;

    private String description;

    private LocalDateTime start;

    private LocalDateTime end;
}
