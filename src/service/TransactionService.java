package service;

import model.Account;
import model.Client;
import model.Transaction;
import java.util.List;
import java.util.function.Predicate;

public interface TransactionService {
    void withdraw(Account account, double amount, String description);

    void deposit(Account account, double amount, String description);

    void transfer(Account source, Account destination, double amount, String description);

    List<Transaction> getTransactions(Client client);
//
//    List<Transaction> filterTransactions(Account account, Predicate<Transaction> filter);
//
//    List<Transaction> detectSuspiciousTransactions(Account account);
}
