package pro.laplacelab.mt4j.example;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;
import pro.laplacelab.mt4j.Strategy;
import pro.laplacelab.mt4j.enums.SignalType;
import pro.laplacelab.mt4j.enums.Timeframe;
import pro.laplacelab.mt4j.model.Advisor;
import pro.laplacelab.mt4j.model.MqlRates;
import pro.laplacelab.mt4j.model.Signal;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class Example implements Strategy {

    @Getter
    public final String name = "EXAMPLE";

    @Override
    public Signal apply(final Advisor advisor, final Map<Timeframe, List<MqlRates>> buffer) {
        // TODO: 23.10.2020 analytic strategy sequences implementation
        return new Signal(advisor.getId(), SignalType.NO_ACTION);
    }

}
