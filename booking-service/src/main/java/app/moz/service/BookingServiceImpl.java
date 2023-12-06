package app.moz.service;

import app.moz.dto.BookingDto;
import app.moz.dto.BookingRequest;
import app.moz.entity.Booking;
import app.moz.repository.BookingRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class BookingServiceImpl implements BookingService{


    private final ModelMapper modelMapper;

    private final BookingRepository bookingRepository;
    @Override
    public List<BookingDto> getAllBookings() {

        List<Booking> bookingList = bookingRepository.findAll();


        return bookingList.stream()
                .map(booking -> modelMapper.map(booking, BookingDto.class))
                .toList();
    }

    @Override
    public BookingDto getOne(int bookingId) {
        Optional<Booking> booking = bookingRepository.findById(bookingId);

        if (booking.isEmpty()) {
            throw new IllegalArgumentException("Not Found");
        }
        return modelMapper.map(booking, BookingDto.class);

    }

    @Override
    public BookingDto createBooking(BookingRequest bookingRequest, int userId, int eventId) {

        try {

            BookingDto bookingDto = BookingDto.builder()
                    .bookingDate(bookingRequest.getBookingDate())
                    .bookingStatus(bookingRequest.getBookingStatus())
                    .userId(userId)
                    .eventId(eventId)
                    .build();

          Booking booking = modelMapper.map(bookingDto, Booking.class);

          Booking booking1 = bookingRepository.save(booking);

            return modelMapper.map(booking1, BookingDto.class);

        } catch (Exception e) {
            throw new IllegalArgumentException("Error Creating User");
        }



    }
}
