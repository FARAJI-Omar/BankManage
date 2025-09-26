package service;

import model.Client;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClientService {
    Client createClient(String firstName, String lastName, String email, String password);

    void deleteClient(UUID clientId);

    // ...existing code...
    List<Client> listClients();
}
