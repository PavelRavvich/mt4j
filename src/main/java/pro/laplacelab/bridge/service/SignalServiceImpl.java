package pro.laplacelab.bridge.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.laplacelab.bridge.model.Config;
import pro.laplacelab.bridge.model.Indicator;
import pro.laplacelab.bridge.model.SignalRequest;
import pro.laplacelab.bridge.model.SignalResponse;
import pro.laplacelab.bridge.scenario.Scenario;
import pro.laplacelab.bridge.scenario.SimpleScenario;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@Service
@AllArgsConstructor
public class SignalServiceImpl implements SignalService {

    private final ConfigService configService;

    private final List<Scenario> scenarios = new CopyOnWriteArrayList<>();

    @PostConstruct
    public void init() {
        scenarios.add(new SimpleScenario());
    }

    @Override
    public SignalResponse get(final SignalRequest request) {
        log.debug("Request: {}", request);
        Config config = configService.get(request.getAdvisorId());
        if (Objects.isNull(config)) {
            throw new RuntimeException("Advisor config not found");
        }
        Scenario scenario = scenarios.stream()
                .filter(item -> request.getScenarioSysName().equals(item.getSysName()))
                .findFirst()
                .orElseThrow(RuntimeException::new);
        List<Indicator> indicators = request.getIndicators();
        SignalResponse response = scenario.apply(config, indicators);
        log.debug("Response: {}", response);
        return response;
    }

}
