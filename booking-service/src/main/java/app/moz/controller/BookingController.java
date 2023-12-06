package app.moz.controller;


import app.moz.dto.BookingDto;
import app.moz.dto.BookingRequest;
import app.moz.service.BookingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @GetMapping("/bookings")
    @ResponseStatus(HttpStatus.OK)
    public List<BookingDto> getAll () {
      return   bookingService.getAllBookings();
    }

    @GetMapping("/bookings/{bookingId}")
   @ResponseStatus(HttpStatus.OK)
    public BookingDto getOne (@PathVariable int bookingId) {
        return bookingService.getOne(bookingId);
    }

    @PostMapping("/bookings/{userId}/{eventId}")
    @ResponseStatus(HttpStatus.CREATED)
    public BookingDto createBooking (@RequestBody BookingRequest bookingRequest,
                                     @PathVariable int userId,
                                     @PathVariable int eventId) {
        return bookingService.createBooking(bookingRequest, userId, eventId);
    }
}
