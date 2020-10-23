package pro.laplacelab.bridge.service;

public interface ConfigService {
    void configure(String config);
    String get(String key);
}
