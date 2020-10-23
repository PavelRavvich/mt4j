package pro.laplacelab.bridge.scenario;

import pro.laplacelab.bridge.model.Config;
import pro.laplacelab.bridge.model.Indicator;
import pro.laplacelab.bridge.model.SignalResponse;

import java.util.List;
import java.util.function.BiFunction;

public interface Scenario extends BiFunction<Config, List<Indicator>, SignalResponse> {
    String getSysName();
}
