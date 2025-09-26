package view;

import controller.BankerController;
import util.InputUtil;

public class BankerView {

    private final BankerController bankerController;

    public BankerView(BankerController bankerController) {
        this.bankerController = bankerController;
    }

    public void bankerView() {
        System.out.println("\n===== Banker Dashboard =====\n");

        while(true){
            System.out.println("1. Manage Clients");
            System.out.println("2. Manage Transactions");
            System.out.println("3. Statistics");
            System.out.println("4. Logout");

            int choice = InputUtil.readInt("\nPlease select an option: ");

            switch(choice) {
                case 1:
                    manageClients();
                    break;
                case 2:
                    manageTransactions();
                    break;
                case 3:
                    statistics();
                    break;
                case 4:
                    System.out.println("Logged out successfully!");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void manageClients() {
        while (true){
            System.out.println("\n== Manage Clients ==\n");
            System.out.println("1. View All Clients");
            System.out.println("2. Add New Client");
            System.out.println("3. Remove Client");
            System.out.println("4. Back to Banker Dashboard");

            int choice = InputUtil.readInt("\nPlease select an option: ");

            switch(choice) {
                case 1:
                    System.out.println("Viewing All Clients... (Not implemented yet)");
                    break;
                case 2:
                    addNewClient();
                    break;
                case 3:
                    System.out.println("Removing Client... (Not implemented yet)");
                    break;
                case 4:
                    System.out.println("\n");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void manageTransactions() {
        while (true){
            System.out.println("\n== Manage Transactions ==\n");
            System.out.println("1. View All Transactions");
            System.out.println("2. View a Client Transactions");
            System.out.println("3. View Suspicious Transactions");
            System.out.println("4. Back to Banker Dashboard");

            int choice = InputUtil.readInt("\nPlease select an option: ");

            switch(choice) {
                case 1:
                    System.out.println("Viewing All Transactions... (Not implemented yet)");
                    break;
                case 2:
                    System.out.println("Viewing a Client Transactions... (Not implemented yet)");
                    break;
                case 3:
                    System.out.println("Viewing Suspicious Transactions... (Not implemented yet)");
                    break;
                case 4:
                    System.out.println("\n");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    public void statistics(){
        System.out.println("\n== Statistics ==\n");
        System.out.println("Viewing Statistics... (Not implemented yet)");
    }

    // ----------------------------------------------

    private void addNewClient() {
        System.out.println("\n=== Add New Client ===\n");

        // View responsibility: Collect input from user
        String firstName = InputUtil.readString("Enter first name");
        String lastName = InputUtil.readString("Enter last name");
        String email = InputUtil.readString("Enter email");
        String password = InputUtil.readString("Enter password");

        bankerController.addNewClient(firstName, lastName, email, password);
    }
}
