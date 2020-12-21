package pro.laplacelab.mt4j.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.laplacelab.mt4j.Strategy;
import pro.laplacelab.mt4j.exception.AdvisorNotFoundException;
import pro.laplacelab.mt4j.exception.StrategyNotFoundException;
import pro.laplacelab.mt4j.model.Advisor;
import pro.laplacelab.mt4j.model.Market;
import pro.laplacelab.mt4j.model.Signal;

import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class SignalServiceImpl implements SignalService {

    private final AdvisorService advisorService;

    private final StrategyService strategyService;

    @Override
    public List<Signal> onTick(final @NotNull Market market) {
        log.debug("Market request: {}", market);
        final Advisor advisor = advisorService
                .findByAdvisorId(market.getAdvisorId())
                .orElseThrow(AdvisorNotFoundException::new);
        final Strategy strategy = strategyService
                .findByName(market.getStrategyName())
                .orElseThrow(StrategyNotFoundException::new);
        final List<Signal> signals = strategy.apply(advisor, market.getRates());
        log.debug("Signals response: {}", signals);
        return signals;
    }

}
