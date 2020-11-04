package pro.laplacelab.bridge.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.laplacelab.bridge.Strategy;
import pro.laplacelab.bridge.exception.AdvisorNotFoundException;
import pro.laplacelab.bridge.exception.StrategyNotFoundException;
import pro.laplacelab.bridge.model.Advisor;
import pro.laplacelab.bridge.model.Market;
import pro.laplacelab.bridge.model.Signal;

import javax.validation.constraints.NotNull;

@Slf4j
@Service
@AllArgsConstructor
public class SignalServiceImpl implements SignalService {

    private final AdvisorService advisorService;

    private final StrategyService strategyService;

    @Override
    public Signal onTick(final @NotNull Market market) {
        log.debug("Market request: {}", market);
        final Advisor advisor = advisorService
                .get(market.getAdvisorId())
                .orElseThrow(AdvisorNotFoundException::new);
        final Strategy strategy = strategyService
                .findByName(market.getStrategyName())
                .orElseThrow(StrategyNotFoundException::new);
        final Signal signal = strategy.apply(advisor, market.getRates());
        log.debug("Signal response: {}", signal);
        return signal;
    }

}
