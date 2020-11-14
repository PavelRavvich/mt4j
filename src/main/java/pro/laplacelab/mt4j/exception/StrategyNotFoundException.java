package pro.laplacelab.mt4j.exception;

public class StrategyNotFoundException extends RuntimeException {
    public StrategyNotFoundException() {
        super("Strategy not found.");
    }
}
