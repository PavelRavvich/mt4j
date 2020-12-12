package pro.laplacelab.mt4j;

import pro.laplacelab.mt4j.enums.Timeframe;
import pro.laplacelab.mt4j.model.Advisor;
import pro.laplacelab.mt4j.model.Rate;
import pro.laplacelab.mt4j.model.Signal;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * Headline integration interface.
 * <p>
 * For strategy implementation:
 * 1. Add unique key to StrategyType enum and return them in getType().
 * 2. Implement strategy logic inside apply method.
 * 3. Strategy implementation is Spring component, @Service or @Component required.
 *
 * @see pro.laplacelab.mt4j.strategy.Example
 */
public interface Strategy
        extends BiFunction<Advisor, Map<Timeframe, List<Rate>>, List<Signal>> {
    List<Signal> apply(Advisor advisor, Map<Timeframe, List<Rate>> rates);

    String getName();
}
