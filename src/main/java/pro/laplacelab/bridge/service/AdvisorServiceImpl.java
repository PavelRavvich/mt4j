package pro.laplacelab.bridge.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.laplacelab.bridge.model.Advisor;
import pro.laplacelab.bridge.model.Input;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class AdvisorServiceImpl implements AdvisorService {

    private final Map<UUID, Advisor> advisors = new ConcurrentHashMap<>();

    @Override
    public Advisor add(final long magic, final @NotNull List<Input> inputs) {
        log.debug("Attempt to build Advisor with inputs: {}", inputs);
        final Advisor advisor = new Advisor(magic, inputs);
        advisors.put(advisor.getId(), advisor);
        log.debug("Add advisor: {}", advisor);
        return advisor;
    }

    @Override
    public Optional<Advisor> get(final @NotNull UUID advisorId) {
        return Optional.of(advisors.get(advisorId));
    }

}
