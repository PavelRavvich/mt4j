package pro.laplacelab.bridge.service;

import pro.laplacelab.bridge.model.Config;

import java.util.UUID;

public interface ConfigService {
    UUID configure(String config);

    Config get(UUID uuid);
}
