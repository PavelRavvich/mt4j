package pro.laplacelab.bridge.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.laplacelab.bridge.model.Advisor;

import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class AdvisorServiceImpl implements AdvisorService {

    private final Map<UUID, Advisor> advisors = new ConcurrentHashMap<>();

    @Override
    public Advisor add(final @NotNull Advisor advisor) {
        log.debug("Attempt to build Advisor from source: {}", advisor);
        final Advisor save = new Advisor(advisor.getMagic(), advisor.getInputs());
        advisors.put(save.getId(), save);
        log.debug("Add advisor: {}", save);
        return save;
    }

    @Override
    public Optional<Advisor> get(final @NotNull UUID advisorId) {
        return Optional.ofNullable(advisors.get(advisorId));
    }

}
