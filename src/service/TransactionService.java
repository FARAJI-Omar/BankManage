package service;

import model.Account;
import model.Client;
import model.Transaction;
import java.util.List;
import java.util.function.Predicate;

public interface TransactionService {
    void withdraw(Account account, double amount, String description);

    double totalWithdrawals(Client client);

    void deposit(Account account, double amount, String description);

    double totalDeposits(Client client);

    void transfer(Account source, Account destination, double amount, String description);

    double totalTransfers(Client client);

    List<Transaction> getTransactions(Client client);

    List<Transaction> filterTransactions(Client client, Predicate<Transaction> filter);

    List<Transaction> sortTransactionsByDate(Client client, boolean ascending);
}
