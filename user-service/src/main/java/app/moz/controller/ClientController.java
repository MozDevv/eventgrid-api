package app.moz.controller;

import app.moz.dto.ClientDto;
import app.moz.dto.ClientRequest;
import app.moz.service.ClientService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class ClientController {

    private final ClientService clientService;


    @GetMapping("/clients")
    @ResponseStatus(HttpStatus.OK)
    public List<ClientDto> getAllClients () {
        return clientService.findAllClients();
    }

    @GetMapping("/new/clients/{clientId}")
    @ResponseStatus(HttpStatus.OK)
    public ClientDto getSingleClient (@PathVariable int clientId) {
        return clientService.findSingleClient(clientId);
    }

    @GetMapping("/clients/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<ClientDto> getUserClients (@PathVariable int userId) {
        return clientService.findUserClients(userId);
    }

    @PostMapping("/new/clients/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ClientDto createClient (@RequestBody ClientRequest clientRequest, @PathVariable long userId) {
        return clientService.createClient(clientRequest, userId);
    }

    @DeleteMapping("/clients/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteClient ( @PathVariable int id ) {
        clientService.deleteClient(id);
    }
}
