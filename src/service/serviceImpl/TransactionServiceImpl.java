package service.serviceImpl;

import exceptions.InsufficientBalanceException;
import model.Account;
import model.Transaction;
import model.enums.TypeTransaction;
import repository.AccountRepository;
import repository.TransactionRepository;
import service.TransactionService;
import util.ValidatorUtil;

import java.time.LocalDateTime;
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

        // Validate amount using ValidatorUtil
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
}
