package repository;

import model.Account;
import model.Client;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepository {
    void save(Account account);
    Optional<Account> findById(UUID id);
    List<Account> findByClient(Client client);
    List<Account> findAll();
    void delete(UUID id);
}
