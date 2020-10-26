package pro.laplacelab.bridge.strategy;

import pro.laplacelab.bridge.enums.StrategyType;
import pro.laplacelab.bridge.model.Advisor;
import pro.laplacelab.bridge.model.Indicator;
import pro.laplacelab.bridge.model.Signal;

import java.util.List;
import java.util.function.BiFunction;

public interface Strategy extends BiFunction<Advisor, List<Indicator>, Signal> {
    StrategyType getType();
}
