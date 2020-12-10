package pro.laplacelab.mt4j;

import pro.laplacelab.mt4j.enums.Timeframe;
import pro.laplacelab.mt4j.model.Advisor;
import pro.laplacelab.mt4j.model.Rate;

import java.util.List;
import java.util.Map;

public interface StrategyCondition {
    Boolean is(Advisor advisor, Map<Timeframe, List<Rate>> rates);
}
