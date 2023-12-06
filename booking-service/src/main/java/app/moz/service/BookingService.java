package app.moz.service;

import app.moz.dto.BookingDto;
import app.moz.dto.BookingRequest;

import java.util.List;

public interface BookingService {

    List<BookingDto> getAllBookings();

    BookingDto getOne(int bookingId);

    BookingDto createBooking(BookingRequest bookingRequest, int userId, int eventId);
}
