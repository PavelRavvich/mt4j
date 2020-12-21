package pro.laplacelab.mt4j.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.laplacelab.mt4j.exception.DuplicateAdvisorException;
import pro.laplacelab.mt4j.model.Advisor;

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
        if (advisors.values().stream().anyMatch(item -> item.getMagic().equals(advisor.getMagic()))) {
            throw new DuplicateAdvisorException(advisor.getMagic());
        }
        final Advisor save = new Advisor(advisor.getMagic(), advisor.getInputs());
        advisors.put(save.getId(), save);
        log.debug("Add advisor: {}", save);
        return save;
    }

    @Override
    public Optional<Advisor> findByAdvisorId(final @NotNull UUID advisorId) {
        return Optional.ofNullable(advisors.get(advisorId));
    }

}
