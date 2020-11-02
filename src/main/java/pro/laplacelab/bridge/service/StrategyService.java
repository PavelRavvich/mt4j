package pro.laplacelab.bridge.service;

import pro.laplacelab.bridge.Strategy;

import java.util.Optional;

public interface StrategyService {
    Optional<Strategy> findByName(String name);
}
