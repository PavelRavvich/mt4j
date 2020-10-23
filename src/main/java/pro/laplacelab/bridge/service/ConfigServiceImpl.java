package pro.laplacelab.bridge.service;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ConfigServiceImpl implements ConfigService {

    private final Map<String, String> cache = new ConcurrentHashMap<>();

    @Override
    public void configure(final String config) {
        Arrays.stream(config.split("\n"))
                .map(keyValue -> keyValue.split("="))
                .forEach(keyValue -> cache.put(keyValue[0], keyValue[1]));
    }

    @Override
    public String get(final String key) {
        return cache.get(key);
    }
}
