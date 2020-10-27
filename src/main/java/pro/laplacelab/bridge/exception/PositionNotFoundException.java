package pro.laplacelab.bridge.exception;

public class PositionNotFoundException extends RuntimeException {
    public PositionNotFoundException() {
        super("Open position not found in target Advisor");
    }
}
