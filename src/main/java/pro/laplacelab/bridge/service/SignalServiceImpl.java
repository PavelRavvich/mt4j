package pro.laplacelab.bridge.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.laplacelab.bridge.exception.AdvisorNotFoundException;
import pro.laplacelab.bridge.exception.StrategyNotFoundException;
import pro.laplacelab.bridge.model.Advisor;
import pro.laplacelab.bridge.model.Market;
import pro.laplacelab.bridge.model.Signal;
import pro.laplacelab.bridge.strategy.Strategy;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@Service
public class SignalServiceImpl implements SignalService {

    private final AdvisorService advisorService;

    private final List<Strategy> strategies;

    @Autowired
    public SignalServiceImpl(final @NotNull List<Strategy> strategies,
                             final @NotNull AdvisorService advisorService) {
        this.strategies = new CopyOnWriteArrayList<>(strategies);
        this.advisorService = advisorService;
    }

    @Override
    public Signal analyze(final @NotNull Market market) {
        log.debug("Market request: {}", market);
        Advisor advisor = advisorService
                .get(market.getAdvisorId())
                .orElseThrow(AdvisorNotFoundException::new);
        Strategy strategy = strategies.stream()
                .filter(item -> item.getType().equals(market.getStrategyType()))
                .findFirst().orElseThrow(StrategyNotFoundException::new);
        Signal signal = strategy.apply(advisor, market.getIndicators());
        log.debug("Signal response: {}", signal);
        return signal;
    }

}
