package pro.laplacelab.mt4j;

import pro.laplacelab.mt4j.model.Advisor;

import java.util.Map;

public interface StrategyCondition<T, R> {

    Boolean is(Advisor advisor, Map<T, R> rates);

}
