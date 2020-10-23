package pro.laplacelab.bridge.scenario;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;
import pro.laplacelab.bridge.model.Config;
import pro.laplacelab.bridge.model.Sequence;
import pro.laplacelab.bridge.model.Signal;

import java.util.List;

@Service
@AllArgsConstructor
public class SimpleScenario implements Scenario {

    @Getter
    private final String sysName = "simple";

    @Override
    public Signal apply(final Config config, final List<Sequence> sequences) {
        // TODO: 23.10.2020 analytic strategy sequences implementation
        return new Signal();
    }

}
