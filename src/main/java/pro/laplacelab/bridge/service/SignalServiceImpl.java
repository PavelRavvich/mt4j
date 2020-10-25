package pro.laplacelab.bridge.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.laplacelab.bridge.model.Properties;
import pro.laplacelab.bridge.model.SignalRequest;
import pro.laplacelab.bridge.model.SignalResponse;
import pro.laplacelab.bridge.strategy.Strategy;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@Service
@AllArgsConstructor
public class SignalServiceImpl implements SignalService {

    private final PropertyService propertyService;

    private final List<Strategy> strategies = new CopyOnWriteArrayList<>();

    @Override
    public SignalResponse get(final @NotNull SignalRequest request) {
        log.debug("Request: {}", request);
        Properties properties = propertyService
                .get(request.getAdvisorId())
                .orElseThrow(RuntimeException::new);
        Strategy strategy = strategies.stream()
                .filter(item -> item.getType().equals(request.getStrategyType()))
                .findFirst().orElseThrow(RuntimeException::new);
        SignalResponse response = strategy.apply(properties, request.getIndicators());
        log.debug("Response: {}", response);
        return response;
    }

    public void addStrategy(final @NotNull Strategy strategy) {
        strategies.add(strategy);
    }

}
