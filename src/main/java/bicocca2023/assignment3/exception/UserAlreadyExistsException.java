package bicocca2023.assignment3.exception;

public class UserAlreadyExistsException extends Exception {
    public UserAlreadyExistsException(){
        super("User already exists.");
    }
}
