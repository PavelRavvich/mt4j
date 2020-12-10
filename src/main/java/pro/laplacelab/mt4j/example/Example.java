package pro.laplacelab.mt4j.example;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;
import pro.laplacelab.mt4j.Strategy;
import pro.laplacelab.mt4j.StrategyCondition;
import pro.laplacelab.mt4j.enums.SignalType;
import pro.laplacelab.mt4j.enums.Timeframe;
import pro.laplacelab.mt4j.model.Advisor;
import pro.laplacelab.mt4j.model.Rate;
import pro.laplacelab.mt4j.model.Signal;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class Example implements Strategy {

    @Getter
    public final String name = "EXAMPLE";

    private final List<StrategyCondition> buyStrategyConditions = new LinkedList<>();

    @PostConstruct
    public void init() {
        buyStrategyConditions.add(
                new StrategyCondition() {
                    @Override
                    public Boolean is(Advisor advisor, Map<Timeframe, List<Rate>> rates) {
                        List<Rate> ratesOneMin = rates.get(Timeframe.M_1);
                        int size = ratesOneMin.size();
                        Rate last = ratesOneMin.get(size - 1);
                        Rate prev = ratesOneMin.get(size - 2);
                        return last.getHigh() > prev.getHigh();
                    }
                }
        );
    }

    @Override
    public List<Signal> apply(final Advisor advisor, final Map<Timeframe, List<Rate>> rates) {
        List<Signal> signals = new ArrayList<>();

        boolean isBuy = buyStrategyConditions.stream()
                .allMatch(buyStrategyCondition ->
                        buyStrategyCondition.is(advisor, rates));

        if (isBuy) {
            Signal buy = new Signal(advisor.getId(), SignalType.BUY, 0.01D, 100, 100);
            signals.add(buy);
        }

        return signals;
    }

}
