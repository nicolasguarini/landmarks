package bicocca2023.assignment3.exception;

public class NotFollowingException extends Exception {
    public NotFollowingException() {
        super("User is not following the other user.");
    }
}
