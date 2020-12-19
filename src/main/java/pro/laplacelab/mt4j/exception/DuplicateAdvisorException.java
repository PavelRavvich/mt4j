package pro.laplacelab.mt4j.exception;

public class DuplicateAdvisorException extends RuntimeException {
    public DuplicateAdvisorException(long magicNumber) {
        super(String.format("Advisor with MagicNumber %s already exist", magicNumber));
    }
}
