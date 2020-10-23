package pro.laplacelab.bridge.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Config {

    private final Map<String, String> map = new HashMap<>();

    public Config(final String config) {
        Arrays.stream(config.split("\n"))
                .map(keyValue -> keyValue.split("="))
                .forEach(keyValue -> {
                    if (Objects.isNull(keyValue[0]) || keyValue[0].isEmpty()
                            || Objects.isNull(keyValue[1]) || keyValue[1].isEmpty()) {
                        throw new RuntimeException("Invalid config content");
                    }
                    map.put(keyValue[0], keyValue[1]);
                });
    }

    public String findByName(final String name) {
        String value = map.get(name);
        if (Objects.isNull(value)) {
            throw new RuntimeException("Config parameter not found");
        }
        return value;
    }
}
