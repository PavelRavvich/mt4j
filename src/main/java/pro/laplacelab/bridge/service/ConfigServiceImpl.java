package pro.laplacelab.bridge.service;

import org.springframework.stereotype.Service;
import pro.laplacelab.bridge.model.Config;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ConfigServiceImpl implements ConfigService {

    //
    private final Map<UUID, Config> configs = new ConcurrentHashMap<>();

    @Override
    public UUID configure(final String conSrc) {
        Config config = new Config(conSrc);
        UUID uuid = UUID.randomUUID();
        configs.put(uuid, config);
        return uuid;
    }

    @Override
    public Config get(final UUID uuid) {
        return configs.get(uuid);
    }
}
