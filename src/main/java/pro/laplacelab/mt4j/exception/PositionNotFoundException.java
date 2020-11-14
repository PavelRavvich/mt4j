package pro.laplacelab.mt4j.exception;

public class PositionNotFoundException extends RuntimeException {
    public PositionNotFoundException() {
        super("Open position not found in target Advisor");
    }
}
