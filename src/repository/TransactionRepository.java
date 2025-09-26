package repository;

import model.Transaction;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository {

    // save new transaction
    void save(Transaction transaction);

    void withdraw(UUID accountId, double amount);

//    void deposit(UUID accountId, double amount);
//
//    void transfer(UUID fromAccountId, UUID toAccountId, double amount);

    // find transactions by account id
    List<Transaction> findByAccountId(UUID accountId);

    // find all transactions
    List<Transaction> findAll();
}
