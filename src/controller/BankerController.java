package controller;

import model.Client;
import service.ClientService;

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
}
