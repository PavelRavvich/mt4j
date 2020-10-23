package pro.laplacelab.bridge.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pro.laplacelab.bridge.model.Sequence;
import pro.laplacelab.bridge.model.Signal;
import pro.laplacelab.bridge.service.strategy.ScenarioService;

import java.util.List;
import java.util.UUID;

@Service
public class SignalServiceImpl implements SignalService {

    private final List<ScenarioService> scenarios;

    public SignalServiceImpl(
            @Qualifier("simpleScenarioService")
                    ScenarioService simpleScenarioService) {
        scenarios = List.of(simpleScenarioService);
    }

    @Override
    public Signal get(final List<Sequence> sequences) {
        // TODO: 23.10.2020
        return new Signal();
    }
}
