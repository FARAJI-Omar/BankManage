package controller;

import model.Account;
import model.Client;
import model.enums.TypeAccount;
import service.AccountService;
import service.ClientService;

import java.util.List;

public class BankerController {

    private final ClientService clientService;
    private final AccountService accountService;

    public BankerController(ClientService clientService, AccountService accountService) {
        this.clientService = clientService;
        this.accountService = accountService;
    }

    public void addNewClientWithAccount(String firstName, String lastName, String email, String password,
                                          TypeAccount accountType, double initialBalance) {
        try {
            // Create new client first
            Client newClient = clientService.createClient(firstName, lastName, email, password);

            // Create new account for the client
            Account newAccount = accountService.createAccount(newClient, accountType, initialBalance);

            System.out.println("\nClient and Account created successfully!");
            System.out.println("\nClient Details:");
            System.out.println("  Name: " + newClient.getFirstName() + " " + newClient.getLastName());
            System.out.println("  Email: " + newClient.getEmail());
            System.out.println("  Password: " + newClient.getPassword());

            System.out.println("\nAccount Details:");
            System.out.println("  Account Type: " + newAccount.getAccountType());
            System.out.println("  Initial Balance: DH" + String.format("%.2f", newAccount.getBalance()));

        } catch (IllegalArgumentException e) {
            System.out.println("\nError: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("\nAn unexpected error occurred: " + e.getMessage());
        }
    }

    public void viewAllClients() {
        List<Client> clients = clientService.listClients();

        if (clients.isEmpty()) {
            System.out.println("No clients found.");
        } else {
            System.out.printf("%-5s %-15s %-15s %-30s %-36s%n", "#", "First Name", "Last Name", "Email", "Client ID");
            System.out.println("─".repeat(105));

            for (int i = 0; i < clients.size(); i++) {
                Client client = clients.get(i);
                System.out.printf("%-5d %-15s %-15s %-30s %-36s%n",
                    (i + 1),
                    client.getFirstName(),
                    client.getLastName(),
                    client.getEmail(),
                    client.getClientId()
                );
            }
            System.out.println("─".repeat(105));
            System.out.println("\nTotal clients: " + clients.size());
        }
    }

    public void removeClient() {
        System.out.println("\n=== Remove Client ===\n");

        List<Client> clients = clientService.listClients();

        viewAllClients();

        System.out.println("\n0. Cancel");

        int choice = util.InputUtil.readInt("\nSelect client to remove (0 to cancel)");

        if (choice == 0) {
            System.out.println("Operation cancelled.");
            return;
        }

        if (choice < 1 || choice > clients.size()) {
            System.out.println("Invalid selection. Please try again.");
            return;
        }

        // Convert choice to array index (choice - 1, index starts at 0)
        Client selectedClient = clients.get(choice - 1);

        try {
            clientService.deleteClient(selectedClient.getClientId());
            System.out.println("\nClient '" + selectedClient.getFirstName() + " " +
                selectedClient.getLastName() + "' has been successfully removed.");
        } catch (Exception e) {
            System.out.println("\nError removing client: " + e.getMessage());
        }
    }
}
