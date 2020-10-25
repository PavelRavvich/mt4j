package pro.laplacelab.bridge.service;

import pro.laplacelab.bridge.model.Input;
import pro.laplacelab.bridge.model.Inputs;
import pro.laplacelab.bridge.model.Ticket;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InputService {
    Ticket save(final long magic, List<Input> properties);

    Optional<Inputs> get(UUID uuid);
}
