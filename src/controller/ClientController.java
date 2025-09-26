package controller;

import controller.HomeController;
import exceptions.InsufficientBalanceException;
import model.Account;
import model.Client;
import model.Transaction;
import model.enums.TypeTransaction;
import service.ClientService;
import service.TransactionService;
import util.InputUtil;

import java.util.List;

public class ClientController {

    private final TransactionService transactionService;
    private final ClientService clientService;

    public ClientController(TransactionService transactionService, ClientService clientService) {
        this.transactionService = transactionService;
        this.clientService = clientService;
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
            System.out.println("New Balance: " + String.format("%.2f", account.getBalance()) + " DH");

        } catch (InsufficientBalanceException e) {
            System.out.println("\n" + e.getMessage());
        } catch (IllegalStateException | IllegalArgumentException e) {
            System.out.println("\nError: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("\nAn unexpected error occurred: " + e.getMessage());
        }
    }

    public void makeDeposit() {
        try {
            Account account = getPrimaryAccount();
            System.out.println("\n=== Make Deposit ===\n");
            System.out.println("Current Balance: " + String.format("%.2f", account.getBalance()) + " DH");

            double amount = InputUtil.readDouble("Enter Deposit amount");
            String description = InputUtil.readString("Enter description (optional)");

            transactionService.deposit(account, amount, description);

            System.out.println("\nDeposit successful!");
            System.out.println("Amount deposited: " + String.format("%.2f", amount) + " DH");
            System.out.println("New Balance: " + String.format("%.2f", account.getBalance()) + " DH");

        } catch (InsufficientBalanceException e) {
            System.out.println("\n" + e.getMessage());
        } catch (IllegalStateException | IllegalArgumentException e) {
            System.out.println("\nError: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("\nAn unexpected error occurred: " + e.getMessage());
        }
    }

    public void makeTransfer() {
        try {
            Account sourceAccount = getPrimaryAccount();
            System.out.println("\n=== Make Transfer ===\n");
            System.out.println("Current Balance: " + String.format("%.2f", sourceAccount.getBalance()) + " DH");

            Client selectedClient = selectTransferRecipient();
            if (selectedClient == null) return;

            Account destinationAccount = selectedClient.getAccounts().get(0);

            double amount = InputUtil.readDouble("Enter transfer amount: ");
            String description = InputUtil.readString("Enter description (optional): ");

            transactionService.transfer(sourceAccount, destinationAccount, amount, description);

            System.out.println("\nTransfer successful!");
            System.out.println("Amount transferred: " + String.format("%.2f", amount) + " DH");
            System.out.println("To: " + selectedClient.getFirstName() + " " + selectedClient.getLastName());
            System.out.println("New Balance: " + String.format("%.2f", sourceAccount.getBalance()) + " DH");

        } catch (InsufficientBalanceException e) {
            System.out.println("\n" + e.getMessage());
        } catch (IllegalStateException | IllegalArgumentException e) {
            System.out.println("\nError: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("\nAn unexpected error occurred: " + e.getMessage());
        }
    }

    private Client selectTransferRecipient() {
        List<Client> allClients = clientService.listClients();
        Client currentClient = (Client) HomeController.getCurrentUser();

        allClients.removeIf(client -> client.getClientId().equals(currentClient.getClientId()));

        if (allClients.isEmpty()) {
            System.out.println("No other clients available for transfer.");
            return null;
        }

        System.out.println("\nAvailable clients for transfer:");
        System.out.println("================================");
        for (int i = 0; i < allClients.size(); i++) {
            Client client = allClients.get(i);
            String fullName = client.getFirstName() + " " + client.getLastName();
            System.out.printf("%d. %s (%s)%n", i + 1, fullName, client.getEmail());
        }

        int clientChoice = InputUtil.readInt("\nSelect recipient (enter number): ");

        if (clientChoice < 1 || clientChoice > allClients.size()) {
            System.out.println("Invalid selection.");
            return null;
        }

        return allClients.get(clientChoice - 1);
    }

    public void viewTransactionHistory() {
        try {
            Client currentClient = (Client) HomeController.getCurrentUser();
            List<Transaction> transactions = transactionService.getTransactions(currentClient);

            System.out.println("\n=== Transaction History ===");

            if (transactions.isEmpty()) {
                System.out.println("No transactions found.");
                return;
            }

            System.out.println("\n# | Type       | Amount     | Description           | Date");
            System.out.println("--|------------|------------|-----------------------|--------------------");

            for (int i = 0; i < transactions.size(); i++) {
                Transaction t = transactions.get(i);
                System.out.printf("%-2d| %-10s | %10.2f | %-21s | %s%n",
                    i + 1,
                    t.getTransactionType(),
                    t.getAmount(),
                    t.getDescription().length() > 21 ? t.getDescription().substring(0, 18) + "..." : t.getDescription(),
                    t.getDate().toString().substring(0, 19)
                );
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
