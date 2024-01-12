package app.moz.service;

import app.moz.dto.ClientDto;
import app.moz.dto.ClientRequest;
import app.moz.entity.Clients;

import java.util.List;

public interface ClientService {

   List<ClientDto> findAllClients();

   List<ClientDto> findUserClients(int userId);

   ClientDto findSingleClient (int clientId);

   ClientDto createClient (ClientRequest clientRequest ,long userId);

   void  deleteClient(int id);
}
