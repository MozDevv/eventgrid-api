package app.moz.service;

import app.moz.Dto.EventDto;
import app.moz.Dto.EventRequest;
import app.moz.entity.Event;

import java.util.List;

public interface EventService {
  List<EventDto> findAll();

  EventDto getById(int id);

  EventDto createEvent(EventRequest eventRequest);

}
