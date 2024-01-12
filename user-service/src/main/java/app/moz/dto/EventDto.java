package app.moz.dto;

import app.moz.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventDto {

    private int id;

    private String title;

    private String description;

    private LocalDateTime start;

    private LocalDateTime end;

    private  long  userId;

}

