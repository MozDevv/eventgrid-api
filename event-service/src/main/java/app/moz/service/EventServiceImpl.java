package app.moz.service;

import app.moz.Dto.EventDto;
import app.moz.Dto.EventRequest;
import app.moz.entity.Event;
import app.moz.repository.EventRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService{

    private final EventRepository eventRepository;
    private final ModelMapper modelMapper;



    @Override
    public List<EventDto> findAll() {

        List<Event> eventList = eventRepository.findAll();

        return eventList.stream()
                .map(event -> modelMapper.map(event, EventDto.class))
                .toList();
    }

    @Override
    public EventDto getById(int id) {

        Optional<Event> eventOptional = eventRepository.findById(id);

        Event event = eventOptional.orElseThrow(() -> new IllegalArgumentException("Event not found"));

        return modelMapper.map(event, EventDto.class);
    }

    @Override
    public EventDto createEvent(EventRequest eventRequest) {

        try {
            EventDto eventDto = EventDto.builder()
                    .eventDate(eventRequest.getEventDate())
                    .eventName(eventRequest.getEventName())
                    .eventVenue(eventRequest.getEventVenue())
                    .isAvailable(eventRequest.getIsAvailable())
                    .build();

            Event event = modelMapper.map(eventDto, Event.class);

            Event event1 = eventRepository.save(event);

            return modelMapper.map(event1, EventDto.class);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error Saving Event");
        }
    }
}

