package app.moz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BookingDto {
    private int bookingId;

    private int eventId;

    private int userId;

    private Date bookingDate;

    private String bookingStatus;
}
