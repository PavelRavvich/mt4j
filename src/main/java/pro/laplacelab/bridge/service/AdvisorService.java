package pro.laplacelab.bridge.service;

import pro.laplacelab.bridge.model.Advisor;

import java.util.Optional;
import java.util.UUID;

public interface AdvisorService {
    Optional<Advisor> get(UUID uuid);

    Advisor add(final Advisor advisor);
}
