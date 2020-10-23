package pro.laplacelab.bridge.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.laplacelab.bridge.model.Config;
import pro.laplacelab.bridge.model.Sequence;
import pro.laplacelab.bridge.model.Signal;
import pro.laplacelab.bridge.model.SignalRequest;
import pro.laplacelab.bridge.scenario.Scenario;
import pro.laplacelab.bridge.scenario.SimpleScenario;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@Slf4j
@AllArgsConstructor
public class SignalServiceImpl implements SignalService {

    private final ConfigService configService;

    // Advisor UUID to Scenario
    private final List<Scenario> scenarios = new CopyOnWriteArrayList<>();

    @PostConstruct
    public void init() {
        scenarios.add(new SimpleScenario());
    }

    @Override
    public Signal get(final SignalRequest request) {
        log.debug("Request: {request}");
        Scenario scenario = scenarios.stream()
                .filter(item ->
                        request.getScenarioSysName().equals(item.getSysName()))
                .findFirst()
                .orElseThrow(RuntimeException::new);

        List<Sequence> sequences = request.getSequences();
        Config config = configService.get(request.getAdvisorId());

        Signal signal = scenario.apply(config, sequences);
        log.debug("Response: {signal}\n");
        return signal;
    }
}
