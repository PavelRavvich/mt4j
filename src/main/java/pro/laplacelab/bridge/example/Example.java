package pro.laplacelab.bridge.example;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;
import pro.laplacelab.bridge.Strategy;
import pro.laplacelab.bridge.enums.SignalType;
import pro.laplacelab.bridge.model.Advisor;
import pro.laplacelab.bridge.model.Indicator;
import pro.laplacelab.bridge.model.Signal;

import java.util.List;

@Service
@AllArgsConstructor
public class Example implements Strategy {

    @Getter
    public final String name = "EXAMPLE";

    @Override
    public Signal apply(final Advisor advisor, final List<Indicator> indicators) {
        // TODO: 23.10.2020 analytic strategy sequences implementation
        return new Signal(advisor.getId(), SignalType.NO_ACTION);
    }

}
