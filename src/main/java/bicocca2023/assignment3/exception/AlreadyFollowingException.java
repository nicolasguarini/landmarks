package bicocca2023.assignment3.exception;

public class AlreadyFollowingException extends Exception {
    public AlreadyFollowingException(){
        super("User is already following this user.st.");
    }
}
