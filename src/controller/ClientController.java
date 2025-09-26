package controller;

import controller.HomeController;
import exceptions.InsufficientBalanceException;
import model.Account;
import model.Client;
import service.TransactionService;
import util.InputUtil;

public class ClientController {

    private final TransactionService transactionService;

    public ClientController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    // Helper method to get client first found account
    public Account getPrimaryAccount() {
        Client currentClient = (Client) HomeController.getCurrentUser();
        return currentClient.getAccounts().get(0);
    }

    public void makeWithdrawal() {
        try {
            Account account = getPrimaryAccount();

            System.out.println("\n=== Make Withdrawal ===\n");
            System.out.println("Current Balance: " + String.format("%.2f", account.getBalance()) + " DH");

            double amount = InputUtil.readDouble("Enter withdrawal amount");
            String description = InputUtil.readString("Enter description (optional)");

            transactionService.withdraw(account, amount, description);

            System.out.println("\nWithdrawal successful!");
            System.out.println("Amount withdrawn: " + String.format("%.2f", amount) + " DH");
            System.out.println("Balance: " + String.format("%.2f", account.getBalance()) + " DH");

        } catch (IllegalStateException e) {
            System.out.println("\nError: " + e.getMessage());
        } catch (InsufficientBalanceException e) {
            System.out.println("\n" + e.getMessage());
        } catch (Exception e) {
            System.out.println("\nAn unexpected error occurred: " + e.getMessage());
        }
    }
}
