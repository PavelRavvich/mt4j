package pro.laplacelab.bridge.service.strategy;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pro.laplacelab.bridge.enums.ScenarioType;
import pro.laplacelab.bridge.model.Sequence;
import pro.laplacelab.bridge.model.Signal;
import pro.laplacelab.bridge.model.Scenario;
import pro.laplacelab.bridge.repository.ScenarioRepository;
import pro.laplacelab.bridge.service.ConfigService;

import java.util.List;

@Service
@AllArgsConstructor
public class SimpleScenarioService implements ScenarioService {
    /**
     * Scenario of strategy like a handling rules.
     */
    private final ScenarioRepository scenarioRepository;
    /**
     * Advisor input parameters config.
     */
    private final ConfigService configService;

    @Override
    public Signal get(final List<Sequence> sequences) {
        Scenario scenario = scenarioRepository.findDistinctByType(ScenarioType.SIMPLE);
        String inaccuracy = configService.get("inaccuracy_factor");
        // TODO: 23.10.2020 analytic strategy sequences implementation
        return new Signal();
    }
}
