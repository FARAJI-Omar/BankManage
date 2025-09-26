package service.serviceImpl;

import exceptions.InsufficientBalanceException;
import model.Account;
import model.Client;
import model.Transaction;
import model.enums.TypeTransaction;
import repository.AccountRepository;
import repository.TransactionRepository;
import service.TransactionService;
import util.ValidatorUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public void withdraw(Account account, double amount, String description) {
        // Validate account
        if (account == null) {
            throw new IllegalArgumentException("Account cannot be null");
        }

        // Validate amount
        if (!ValidatorUtil.isValidWithdrawAmount(amount)) {
            if (amount <= 0) {
                throw new IllegalArgumentException("Withdrawal amount must be positive");
            } else {
                throw new IllegalArgumentException("Withdrawal amount exceeds limit of 10,000 DH");
            }
        }

        // Check if sufficient balance
        if (account.getBalance() < amount) {
            throw new InsufficientBalanceException("Insufficient balance. Available: " +
                String.format("%.2f", account.getBalance()) + " DH, Requested: " +
                String.format("%.2f", amount) + " DH");
        }

        // Update account balance
        account.setBalance(account.getBalance() - amount);

        // Create withdrawal transaction
        Transaction withdrawal = new Transaction(
            UUID.randomUUID(),
            TypeTransaction.WITHDRAWAL,
            amount,
            LocalDateTime.now(),
            description != null && !description.trim().isEmpty() ? description : "Withdraw",
            account,
            null // No destination account for withdrawal
        );

        // Save transaction to repository
        transactionRepository.save(withdrawal);

        // Add transaction to account's transaction list
        account.getTransactions().add(withdrawal);
    }

    @Override
    public void deposit(Account account, double amount, String description) {
        // Validate account
        if (account == null) {
            throw new IllegalArgumentException("Account cannot be null");
        }

        // Validate amount
        if (!ValidatorUtil.isValidDepositAmount(amount)) {
            if (amount <= 0) {
                throw new IllegalArgumentException("Deposit amount must be positive");
            } else {
                throw new IllegalArgumentException("Deposit amount exceeds limit of 20,000 DH");
            }
        }

        // Update account balance
        account.setBalance(account.getBalance() + amount);

        // Create Deposit transaction
        Transaction deposit = new Transaction(
                UUID.randomUUID(),
                TypeTransaction.DEPOSIT,
                amount,
                LocalDateTime.now(),
                description != null && !description.trim().isEmpty() ? description : "Deposit",
                account,
                null // No destination account for deposit
        );

        // Save transaction to repository
        transactionRepository.save(deposit);

        // Add transaction to account's transaction list
        account.getTransactions().add(deposit);
    }

    @Override
    public void transfer(Account source, Account destination, double amount, String description) {
        // Validate destination account
        if (destination == null) {
            throw new IllegalArgumentException("Destination account cannot be null");
        }

        // Validate amount
        if (!ValidatorUtil.isValidTransferAmount(amount)) {
            if (amount <= 0) {
                throw new IllegalArgumentException("Transfer amount must be positive");
            } else {
                throw new IllegalArgumentException("Transfer amount exceeds limit of 30,000 DH");
            }
        }

        // Check if sufficient balance in source account
        if (source.getBalance() < amount) {
            throw new InsufficientBalanceException("Insufficient balance. Available: " +
                String.format("%.2f", source.getBalance()) + " DH, Requested: " +
                String.format("%.2f", amount) + " DH");
        }

        // Update account balances
        source.setBalance(source.getBalance() - amount);
        destination.setBalance(destination.getBalance() + amount);

        // Create transfer transaction
        Transaction transfer = new Transaction(
            UUID.randomUUID(),
            TypeTransaction.TRANSFER,
            amount,
            LocalDateTime.now(),
            description != null && !description.trim().isEmpty() ? description : "Transfer",
            source,
            destination
        );

        // Save transaction to repository
        transactionRepository.save(transfer);

        // Add transaction to both accounts' transaction lists
        source.getTransactions().add(transfer);
        destination.getTransactions().add(transfer);
    }

    @Override
    public List<Transaction> getTransactions(Client client) {
        // Get client's primary account
        Account account = client.getAccounts().get(0);

        // Get transactions and sort by date
        List<Transaction> transactions = account.getTransactions();
        transactions.sort((t1, t2) -> t1.getDate().compareTo(t2.getDate()));

        return transactions;
    }
}
