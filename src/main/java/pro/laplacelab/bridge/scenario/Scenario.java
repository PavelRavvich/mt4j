package pro.laplacelab.bridge.scenario;

import pro.laplacelab.bridge.model.Config;
import pro.laplacelab.bridge.model.Sequence;
import pro.laplacelab.bridge.model.Signal;

import java.util.List;
import java.util.function.BiFunction;

public interface Scenario extends BiFunction<Config, List<Sequence>, Signal> {
    String getSysName();
}
