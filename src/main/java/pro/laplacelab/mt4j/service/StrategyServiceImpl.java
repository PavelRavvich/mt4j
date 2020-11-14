package pro.laplacelab.mt4j.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pro.laplacelab.mt4j.Strategy;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StrategyServiceImpl implements StrategyService {

    private final List<Strategy> strategies;

    @Override
    public Optional<Strategy> findByName(final @NotNull String name) {
        return strategies.stream().filter(item -> name.equals(item.getName())).findFirst();
    }
}
