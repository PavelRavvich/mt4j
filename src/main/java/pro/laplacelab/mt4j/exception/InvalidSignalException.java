package pro.laplacelab.mt4j.exception;

public class InvalidSignalException extends RuntimeException {
    public InvalidSignalException() {
        super("Signal can't be constructed. Wrong constructor called.");
    }
}
