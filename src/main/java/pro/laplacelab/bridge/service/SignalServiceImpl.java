package pro.laplacelab.bridge.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.laplacelab.bridge.model.Inputs;
import pro.laplacelab.bridge.model.Market;
import pro.laplacelab.bridge.model.Signal;
import pro.laplacelab.bridge.strategy.Strategy;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@Service
public class SignalServiceImpl implements SignalService {

    private final InputService inputService;

    private final List<Strategy> strategies;

    @Autowired
    public SignalServiceImpl(final List<Strategy> strategies,
                             final InputService inputService) {
        this.strategies = new CopyOnWriteArrayList<>(strategies);
        this.inputService = inputService;
    }

    @Override
    public Signal analyze(final @NotNull Market request) {
        log.debug("Request: {}", request);
        Inputs inputs = inputService
                .get(request.getAdvisorId())
                .orElseThrow(RuntimeException::new);
        Strategy strategy = strategies.stream()
                .filter(item -> item.getType().equals(request.getStrategyType()))
                .findFirst().orElseThrow(RuntimeException::new);
        Signal response = strategy.apply(inputs, request.getIndicators());
        log.debug("Response: {}", response);
        return response;
    }

}
