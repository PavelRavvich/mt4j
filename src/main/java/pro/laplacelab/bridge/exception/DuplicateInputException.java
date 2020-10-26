package pro.laplacelab.bridge.exception;

public class DuplicateInputException extends RuntimeException {
    public DuplicateInputException() {
        super("Inputs can't be constructed. Duplicate not allowed in Input list.");
    }
}
