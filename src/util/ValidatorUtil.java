package util;

public class ValidatorUtil {
    // email validation regex
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email != null && email.matches(emailRegex);
    }

    // password validation
    public static boolean isValidPassword(String password) {
        // Password must be at least 6 characters long
        return password != null && password.length() >= 6;
    }

    // name validation
    public static boolean isValidName(String name) {
        String nameRegex = "^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$";
        return name != null && name.matches(nameRegex);
    }

    // withdrawal amount validation
    public static boolean isValidWithdrawAmount(double amount) {
        return amount > 0 && amount <= 10000; // Max withdrawal limit of 10,000 DH
    }
}
