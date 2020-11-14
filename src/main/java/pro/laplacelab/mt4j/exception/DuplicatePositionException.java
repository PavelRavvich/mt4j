package pro.laplacelab.mt4j.exception;

public class DuplicatePositionException extends RuntimeException {
    public DuplicatePositionException() {
        super("Position already exist. Duplicate position not allowed.");
    }
}
