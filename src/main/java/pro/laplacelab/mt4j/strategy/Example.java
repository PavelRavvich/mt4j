package pro.laplacelab.mt4j.strategy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.ta4j.core.BarSeries;
import pro.laplacelab.mt4j.Strategy;
import pro.laplacelab.mt4j.StrategyCondition;
import pro.laplacelab.mt4j.adapter.Adapter;
import pro.laplacelab.mt4j.enums.SignalType;
import pro.laplacelab.mt4j.enums.Timeframe;
import pro.laplacelab.mt4j.model.Advisor;
import pro.laplacelab.mt4j.model.Rate;
import pro.laplacelab.mt4j.model.Signal;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class Example implements Strategy {

    private final Adapter<Duration, BarSeries> adapterTa4J;

    @Getter
    public final String name = "EXAMPLE";

    private final List<StrategyCondition<Timeframe, Rate>> buyStrategyConditions = new LinkedList<>();

    @PostConstruct
    public void init() {
        buyStrategyConditions.add(
                new StrategyCondition<Timeframe, Rate>() {
                    @Override
                    public Boolean is(final Advisor advisor, final Map<Timeframe, List<Rate>> rates) {
                        final List<Rate> ratesOneMin = rates.get(Timeframe.M_1);
                        int size = ratesOneMin.size();
                        final Rate last = ratesOneMin.get(size - 1);
                        final Rate prev = ratesOneMin.get(size - 2);
                        return last.getHigh() > prev.getHigh();
                    }
                }
        );
    }

    @Override
    public List<Signal> apply(final Advisor advisor, final Map<Timeframe, List<Rate>> rates) {
        /* Handle barSeries with ta4j lib. */
        final Map<Duration, BarSeries> barSeries = adapterTa4J.map(rates);

        /* Handle original Meta Trader Rates. */
        final List<Signal> signals = new ArrayList<>();
        final boolean isBuy = buyStrategyConditions.stream()
                .allMatch(buyStrategyCondition ->
                        buyStrategyCondition.is(advisor, rates));
        if (isBuy) {
            final Signal buy = new Signal(advisor.getId(), SignalType.BUY, 0.01D, 100, 100);
            signals.add(buy);
        }

        return signals;
    }

}
