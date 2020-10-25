package pro.laplacelab.bridge.strategy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;
import pro.laplacelab.bridge.enums.StrategyType;
import pro.laplacelab.bridge.model.Indicator;
import pro.laplacelab.bridge.model.Properties;
import pro.laplacelab.bridge.model.SignalResponse;

import java.util.List;

@Service
@AllArgsConstructor
public class SimpleStrategy implements Strategy {

    @Getter
    private final StrategyType type = StrategyType.SIMPLE;

    @Override
    public SignalResponse apply(final Properties properties, final List<Indicator> indicators) {
        // TODO: 23.10.2020 analytic strategy sequences implementation
        return new SignalResponse();
    }

}
