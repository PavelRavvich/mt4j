package pro.laplacelab.mt4j.service;

import pro.laplacelab.mt4j.Strategy;

import java.util.Optional;

public interface StrategyService {
    Optional<Strategy> findByName(String name);
}
