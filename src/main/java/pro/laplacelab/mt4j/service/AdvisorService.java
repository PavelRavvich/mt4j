package pro.laplacelab.mt4j.service;

import pro.laplacelab.mt4j.model.Advisor;

import java.util.Optional;
import java.util.UUID;

public interface AdvisorService {
    Optional<Advisor> findByAdvisorId(UUID uuid);
    Advisor add(final Advisor advisor);
}
