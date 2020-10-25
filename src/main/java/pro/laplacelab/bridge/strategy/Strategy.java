package pro.laplacelab.bridge.strategy;

import pro.laplacelab.bridge.enums.StrategyType;
import pro.laplacelab.bridge.model.Indicator;
import pro.laplacelab.bridge.model.Properties;
import pro.laplacelab.bridge.model.SignalResponse;

import java.util.List;
import java.util.function.BiFunction;

public interface Strategy extends BiFunction<Properties, List<Indicator>, SignalResponse> {
    StrategyType getType();
}
