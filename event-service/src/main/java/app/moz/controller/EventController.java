package app.moz.controller;

import app.moz.Dto.EventDto;
import app.moz.Dto.EventRequest;
import app.moz.service.EventService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class EventController {

    private final EventService eventService;

    @GetMapping("/events")
    public List<EventDto> getAllEvents() {
        return eventService.findAll();
    }

    @PostMapping("/events")
    @ResponseStatus(HttpStatus.CREATED)
    public EventDto createEvent (@RequestBody EventRequest eventRequest) {
        return eventService.createEvent(eventRequest);
    }

    @GetMapping("/events/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EventDto getOneEvent (@PathVariable int id) {
        return eventService.getById(id);
    }

}
