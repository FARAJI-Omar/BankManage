package controller;

import exceptions.AccountNotFoundException;
import model.Person;
import model.enums.Role;
import repository.InMemoryImpl.InMemoryClientRepository;
import service.serviceImpl.AuthServiceImpl;
import util.ValidatorUtil;
import view.BankerView;
import view.ClientView;

import java.util.Optional;

public class HomeController {
    public static final AuthServiceImpl authService = new AuthServiceImpl(new InMemoryClientRepository());
    public static Person currentUser = null; //track the logged-in user

    public static boolean authenticateUser(String email, String password, int roleChoice) {
        // Validate inputs
        if (!ValidatorUtil.isValidEmail(email)) {
            System.out.println("Invalid email format. Try again.");
            return false;
        }

        if (!ValidatorUtil.isValidPassword(password)) {
            System.out.println("Invalid password format. Try again.");
            return false;
        }

        try {
            Optional<Person> loggedUser = authService.login(email, password);
            if (loggedUser.isPresent()) {
                currentUser = loggedUser.get();
                Role expectedRole = (roleChoice == 1) ? Role.CLIENT : Role.BANKER;

                if (currentUser.getRole() == expectedRole) {
                    System.out.println("Welcome " + currentUser.getFirstName() + " to the " +
                        (expectedRole == Role.CLIENT ? "Client" : "Banker") + " Dashboard!");

                    // navigate to appropriate view
                    if (expectedRole == Role.CLIENT) {
                        ClientView.clientView();
                    } else {
                        BankerView.bankerView();
                    }
                    return true;
                } else {
                    System.out.println("Access denied.");
                    currentUser = null;
                    return false;
                }
            }
            return false;
        } catch (AccountNotFoundException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static Person getCurrentUser() {
        return currentUser;
    }
}
