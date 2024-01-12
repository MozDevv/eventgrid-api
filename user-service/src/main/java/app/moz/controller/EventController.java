package app.moz.controller;

import app.moz.dto.EventDto;
import app.moz.dto.EventRequest;
import app.moz.service.EventService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = {"Authorization"})
public class EventController {

    private final EventService eventService;

    @PostMapping("/new/events/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public EventDto createEvent(@RequestBody EventRequest eventRequest, @PathVariable long id) {

        return eventService.createEvent(eventRequest, id);
    }


    @GetMapping("/event/{id}")
    @ResponseStatus(HttpStatus.OK)
    public  EventDto getEventById (@PathVariable int id) {
        return eventService.getEventById(id);
    }


    @GetMapping("/events")
    @ResponseStatus(HttpStatus.OK)
    public List<EventDto> getAllEvents() {
        return eventService.getAllEvents();
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/events/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<EventDto> getSingleUserAllEvents(@PathVariable long id) {

        return eventService.getSingleUserEvents(id);
    }


}
