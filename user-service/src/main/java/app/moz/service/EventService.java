package app.moz.service;

import app.moz.dto.EventDto;
import app.moz.dto.EventRequest;

import java.util.List;

public interface EventService {

    EventDto createEvent(EventRequest eventRequest , long userId);

    List<EventDto> getAllEvents();

    List<EventDto> getSingleUserEvents(long id);

    void  deleteEvents(int id);

}
