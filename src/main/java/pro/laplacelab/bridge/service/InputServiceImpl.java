package pro.laplacelab.bridge.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.laplacelab.bridge.model.Input;
import pro.laplacelab.bridge.model.Inputs;
import pro.laplacelab.bridge.model.Ticket;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class InputServiceImpl implements InputService {

    private final Map<UUID, Inputs> inputCache = new ConcurrentHashMap<>();

    @Override
    public Ticket save(final long magic, final @NotNull List<Input> inputList) {
        log.debug("Attempt to add property list: {}", inputList);
        final Inputs inputs = new Inputs(magic, inputList);
        inputCache.put(inputs.getId(), inputs);
        log.debug("Add advisor Properties {}", inputs);
        return new Ticket(inputs.getId());
    }

    @Override
    public Optional<Inputs> get(final @NotNull UUID advisorId) {
        return Optional.of(inputCache.get(advisorId));
    }

}
