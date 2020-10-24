package pro.laplacelab.bridge.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.laplacelab.bridge.model.Config;
import pro.laplacelab.bridge.model.Indicator;
import pro.laplacelab.bridge.model.SignalRequest;
import pro.laplacelab.bridge.model.SignalResponse;
import pro.laplacelab.bridge.strategy.SimpleStrategy;
import pro.laplacelab.bridge.strategy.Strategy;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@Service
@AllArgsConstructor
public class SignalServiceImpl implements SignalService {

    private final ConfigService configService;

    private final List<Strategy> strategies = new CopyOnWriteArrayList<>();

    @PostConstruct
    public void init() {
        strategies.add(new SimpleStrategy());
    }

    @Override
    public SignalResponse get(final SignalRequest request) {
        log.debug("Request: {}", request);
        Config config = configService.get(request.getAdvisorId());
        if (Objects.isNull(config)) {
            throw new RuntimeException("Advisor config not found");
        }
        Strategy strategy = strategies.stream()
                .filter(item -> request.getStrategySysName().equals(item.getSysName()))
                .findFirst()
                .orElseThrow(RuntimeException::new);
        List<Indicator> indicators = request.getIndicators();
        SignalResponse response = strategy.apply(config, indicators);
        log.debug("Response: {}", response);
        return response;
    }

}
