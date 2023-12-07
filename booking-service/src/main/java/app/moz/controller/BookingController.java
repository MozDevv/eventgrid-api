package app.moz.controller;


import app.moz.dto.BookingDto;
import app.moz.dto.BookingRequest;
import app.moz.service.BookingService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    @CircuitBreaker(name = "event", fallbackMethod = "fallbackMethod")
    public BookingDto createBooking (@RequestBody BookingRequest bookingRequest,
                                     @PathVariable int userId,
                                     @PathVariable int eventId) {
        return bookingService.createBooking(bookingRequest, userId, eventId);
    }

    public BookingDto fallbackMethod(BookingRequest bookingRequest, int userId, int eventId, Throwable throwable
    ) {
        System.out.println("Fallback: Something went wrong with the booking service.");

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking service is unavailable");
    }

}
