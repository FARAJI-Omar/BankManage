package repository;

import model.Client;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClientRepository {

    // save new client
    void save(Client client);

    // find client by id
    Optional<Client> findById(UUID id);

    // find client by email
    Optional<Client> findByEmail(String email);

    // find all clients
    List<Client> findAll();

    // delete client by id
    void delete(UUID id);
}
