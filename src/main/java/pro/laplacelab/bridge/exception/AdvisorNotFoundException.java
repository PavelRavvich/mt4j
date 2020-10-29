package pro.laplacelab.bridge.exception;

public class AdvisorNotFoundException extends RuntimeException {
    public AdvisorNotFoundException() {
        super("Advisor not found. Advisor should be registered before.");
    }
}
