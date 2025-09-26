package view;

import controller.HomeController;
import controller.ClientController;
import model.Client;
import model.Account;
import util.InputUtil;

public class ClientView {
    
    private final ClientController clientController;
    
    public ClientView(ClientController clientController) {
        this.clientController = clientController;
    }
    
    public void clientView() {
        while(true){
            Client currentClient = (Client) HomeController.getCurrentUser();

            System.out.println("\n===== Client Dashboard =====\n");

            if (currentClient != null) {
                System.out.println("Welcome Mr. " + currentClient.getFirstName());

                try {
                    Account primaryAccount = clientController.getPrimaryAccount();
                    System.out.println("•" + primaryAccount.getAccountType() + " Account: " + primaryAccount.getAccountId());
                    System.out.println("•Balance: " + String.format("%.2f", primaryAccount.getBalance()) + " DH");
                } catch (IllegalStateException e) {
                    System.out.println("•No account information available");
                }
                System.out.println();
            }

            System.out.println("1. Make Transaction");
            System.out.println("2. View Transaction History");
            System.out.println("3. Logout");

            int choice = InputUtil.readInt("Please select an option: ");

            switch(choice) {
                case 1:
                    showTransactionMenu();
                    break;
                case 2:
                    clientController.viewTransactionHistory();
                    break;
                case 3:
                    System.out.println("Logged out successfully!");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void showTransactionMenu() {
        while (true) {
            System.out.println("\n=== Transaction Menu ===\n");
            System.out.println("1. Withdraw");
            System.out.println("2. Deposit");
            System.out.println("3. Transfer");
            System.out.println("4. Back to Main Menu");

            int choice = InputUtil.readInt("Please select transaction type: ");
            System.out.println("\n");

            switch (choice) {
                case 1:
                    clientController.makeWithdrawal();
                    break;
                case 2:
                    clientController.makeDeposit();
                    break;
                case 3:
                    clientController.makeTransfer();
                    break;
                case 4:
                    return; // Go back to main menu
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
