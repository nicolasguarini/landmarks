package bicocca2023.assignment3.exception;

public class UserNotFoundException extends Exception {
    public UserNotFoundException() {
        super("User not found.");
    }
}
