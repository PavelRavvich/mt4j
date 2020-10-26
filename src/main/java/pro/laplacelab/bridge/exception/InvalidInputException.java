package pro.laplacelab.bridge.exception;

public class InvalidInputException extends RuntimeException {
    public InvalidInputException() {
        super("Input can't be constructed. Parameters can't be null or empty.");
    }
}
