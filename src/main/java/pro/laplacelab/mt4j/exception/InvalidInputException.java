package pro.laplacelab.mt4j.exception;

public class InvalidInputException extends RuntimeException {
    public InvalidInputException() {
        super("Input can't be constructed. Parameters can't be null or empty.");
    }
}
