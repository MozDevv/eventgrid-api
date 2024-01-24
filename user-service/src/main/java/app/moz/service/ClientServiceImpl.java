package app.moz.service;

import app.moz.dto.ClientBookedEvent;
import app.moz.dto.ClientDto;
import app.moz.dto.ClientRequest;
import app.moz.entity.Clients;
import app.moz.entity.User;
import app.moz.repository.ClientRepository;
import app.moz.repository.UserRepository;
import ch.qos.logback.core.net.server.Client;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClientServiceImpl implements  ClientService {

    private final ClientRepository clientRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
   private final KafkaTemplate<String, ClientBookedEvent> kafkaTemplate;

    @Override
    public List<ClientDto> findAllClients() {

        List<Clients> clientList = clientRepository.findAll();

        List<ClientDto> clientDtoList = clientList.stream()
                .map(clients -> modelMapper.map(clients, ClientDto.class))
                .collect(Collectors.toList());
        return clientDtoList;

    }

    @Override
    public List<ClientDto> findUserClients(int userId) {

        List<Clients> clientsList = clientRepository.findByUser_Id(userId);

        List<ClientDto> clientDtoList = clientsList.stream()
                .map(clients -> modelMapper.map(clients, ClientDto.class))
                .toList();

        return clientDtoList;

    }


    @Override
    public ClientDto findSingleClient(int clientId) {

        Optional<Clients> client = clientRepository.findById(clientId);

        if (client.isEmpty()) {
            throw new IllegalArgumentException("Not Found");
        }

        return modelMapper.map(client.get(), ClientDto.class);

    }

    @Override
    public ClientDto createClient(ClientRequest clientRequest, long userId) {

        Clients client = new Clients();

        client.setClientName(clientRequest.getClientName());

        client.setEmail(clientRequest.getEmail());

        client.setPhoneNumber(clientRequest.getPhoneNumber());

        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            throw new IllegalArgumentException("Not found");
        }

        client.setUser(user.get());

        Clients client1 = clientRepository.save(client);

        if (clientRequest.getLink() !=null && !clientRequest.getLink().isEmpty() )    {
            kafkaTemplate.send("notificationTopic", new ClientBookedEvent(
                    client1.getEmail(),
                    clientRequest.getLink(),
                    clientRequest.getClientName()

            ));
        }


        return modelMapper.map(client1, ClientDto.class);
    }

    @Override
    public void deleteClient(int id) {
        Optional<Clients> client = clientRepository.findById(id);
        if (client.isEmpty()) {
            throw new IllegalArgumentException("Not Found");
        }

        clientRepository.delete(client.get());
    }
}
