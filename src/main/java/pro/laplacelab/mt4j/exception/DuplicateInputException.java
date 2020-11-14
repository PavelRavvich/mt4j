package pro.laplacelab.mt4j.exception;

public class DuplicateInputException extends RuntimeException {
    public DuplicateInputException() {
        super("Advisor can't be constructed. Duplicate input key not allowed.");
    }
}
