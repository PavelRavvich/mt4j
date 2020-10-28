package pro.laplacelab.bridge.exception;

public class DuplicatePositionException extends RuntimeException {
    public DuplicatePositionException() {
        super("Position already exist. Duplicate position not allowed.");
    }
}
