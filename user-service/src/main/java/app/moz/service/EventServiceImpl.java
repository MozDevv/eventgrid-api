package app.moz.service;

import app.moz.dto.EventDto;
import app.moz.dto.EventRequest;
import app.moz.entity.Event;
import app.moz.entity.User;
import app.moz.repository.EventRepository;
import app.moz.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;


    @Override
    public EventDto createEvent(EventRequest eventRequest, long userId) {

        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("User was not Found");
        }

        User user = userOptional.get();

        Event event = new Event();

        event.setDescription(eventRequest.getDescription());
        event.setTitle(eventRequest.getTitle());
        event.setStart(eventRequest.getStart());
        event.setEnd(eventRequest.getEnd());


        event.setUser(user);

        Event event1 = eventRepository.save(event);
        
        EventDto eventDto = modelMapper.map(event1, EventDto.class);

        eventDto.setUserId(userId);

        return eventDto;
    }

    @Override
    public List<EventDto> getAllEvents() {

        List<Event> events = eventRepository.findAll();

        return events.stream().map(event -> modelMapper.map(event, EventDto.class)).collect(Collectors.toList());
    }

    @Override
    public EventDto getEventById(int id) {

        Optional<Event> eventOptional = eventRepository.findById(id);

        if (eventOptional.isEmpty()) {
            throw new IllegalArgumentException("Not found");
        }

        Event event = eventOptional.get();

        return modelMapper.map(event, EventDto.class);

    }

    @Override
    public List<EventDto> getSingleUserEvents(long id) {

        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("User Was Not found");
        }
        User user = userOptional.get();

        List<Event> eventList = eventRepository.findByUser(user);

        return eventList.stream().map(event -> modelMapper.map(event, EventDto.class)).toList();

    }

    @Override
    public void deleteEvents(int id) {

        Optional<Event> eventOptional = eventRepository.findById(id);

        if (eventOptional.isEmpty()) {
            throw new IllegalArgumentException("Not Found");
        }

        Event event = eventOptional.get();

        eventRepository.delete(event);


    }

    @Override
    public EventDto findEventByDescription(String email) {

        Event event = eventRepository.findByDescription(email);


        return null;
    }


}
