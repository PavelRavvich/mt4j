package pro.laplacelab.bridge.scenario;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;
import pro.laplacelab.bridge.model.Config;
import pro.laplacelab.bridge.model.Indicator;
import pro.laplacelab.bridge.model.SignalResponse;

import java.util.List;

@Service
@AllArgsConstructor
public class SimpleScenario implements Scenario {

    @Getter
    private final String sysName = "simple";

    @Override
    public SignalResponse apply(final Config config, final List<Indicator> indicators) {
        // TODO: 23.10.2020 analytic strategy sequences implementation
        return new SignalResponse();
    }

}
