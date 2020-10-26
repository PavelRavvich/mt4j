package pro.laplacelab.bridge.exception;

public class DuplicateInputException extends RuntimeException {
    public DuplicateInputException() {
        super("Advisor can't be constructed. Duplicate input key not allowed.");
    }
}
