package controller;

import model.Client;
import service.ClientService;

import java.util.List;

public class BankerController {

    private final ClientService clientService;

    public BankerController(ClientService clientService) {
        this.clientService = clientService;
    }

    public boolean addNewClient(String firstName, String lastName, String email, String password) {
        try {
            Client newClient = clientService.createClient(firstName, lastName, email, password);

            System.out.println("\nClient created successfully!");
            System.out.println("| Name: " + newClient.getFirstName() + " " + newClient.getLastName());
            System.out.println("| Email: " + newClient.getEmail());
            System.out.println("| Password : " + newClient.getPassword());

            return true;

        } catch (IllegalArgumentException e) {
            System.out.println("\nError: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("\nAn unexpected error occurred: " + e.getMessage());
            return false;
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
