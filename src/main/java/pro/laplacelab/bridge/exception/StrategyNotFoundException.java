package pro.laplacelab.bridge.exception;

public class StrategyNotFoundException extends RuntimeException {
    public StrategyNotFoundException() {
        super("Strategy not found.");
    }
}
