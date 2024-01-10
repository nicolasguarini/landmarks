package bicocca2023.assignment3.exception;

public class LandmarkNotFoundException extends Exception {
    public LandmarkNotFoundException() {
        super("Landmark not found.");
    }
}
