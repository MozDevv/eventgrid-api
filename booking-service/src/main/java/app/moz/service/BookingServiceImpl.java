package app.moz.service;

import app.moz.dto.BookingDto;
import app.moz.dto.BookingRequest;
import app.moz.dto.EventDto;
import app.moz.entity.Booking;
import app.moz.exc.BookingCreationException;
import app.moz.exc.EventNotAvailableException;
import app.moz.exc.EventNotFoundException;
import app.moz.repository.BookingRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Slf4j
public class BookingServiceImpl implements BookingService{


    private final ModelMapper modelMapper;

    private final BookingRepository bookingRepository;

    private final WebClient.Builder webClient;
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


        //call the event service, check if event is available

      EventDto event =  webClient.build().get()
                .uri("http://event-service/api/v1/events/{eventId}", eventId)
                .retrieve()
                .bodyToMono(EventDto.class)
                .block();


      if (event == null) {
          throw new EventNotFoundException("Not Found");
      } else if (!event.getIsAvailable()) {
          throw new EventNotAvailableException("Event is not available right now");
      }

        System.out.println(event);
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
            log.error(e.getMessage());
            throw new BookingCreationException("Error Creating User");
        }

    }
}
