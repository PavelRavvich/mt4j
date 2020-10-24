package pro.laplacelab.bridge.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.laplacelab.bridge.model.Config;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class ConfigServiceImpl implements ConfigService {

    private final Map<UUID, Config> configs = new ConcurrentHashMap<>();

    @Override
    public UUID add(final String confSrc) {
        log.debug("Attempt to register advisor with configuration: {}", confSrc);
        Config config = new Config(confSrc);
        UUID uuid = UUID.randomUUID();
        configs.put(uuid, config);
        log.debug("Advisor config {} registered successfully with UUID: {}", config, uuid);
        return uuid;
    }

    @Override
    public Config get(final UUID uuid) {
        return configs.get(uuid);
    }

}
