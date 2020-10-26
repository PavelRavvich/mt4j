package pro.laplacelab.bridge;

import pro.laplacelab.bridge.enums.StrategyType;
import pro.laplacelab.bridge.model.Advisor;
import pro.laplacelab.bridge.model.Indicator;
import pro.laplacelab.bridge.model.Signal;

import java.util.List;
import java.util.function.BiFunction;

/**
 * Headline integration interface.
 * <p>
 * For strategy implementation:
 * 1. Add unique key to StrategyType enum and return them in getType().
 * 2. Implement strategy logic inside apply method.
 * 3. Strategy implementation is Spring component, @Service or @Component required.
 *
 * @see pro.laplacelab.bridge.strategy.Example
 */
public interface Strategy extends BiFunction<Advisor, List<Indicator>, Signal> {
    Signal apply(Advisor advisor, List<Indicator> indicators);
    StrategyType getType();
}
