package pro.laplacelab.mt4j.service;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import pro.laplacelab.mt4j.Strategy;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@WebAppConfiguration
@RunWith(SpringRunner.class)
@DisplayName("StrategyService test")
public class StrategyServiceImplTest {

    @Autowired
    StrategyService strategyService;

    @Autowired
    Strategy exampleStrategy;

    @Test
    @DisplayName("When strategy not found then return empty optional")
    public void whenStrategyNotFoundThenReturnEmptyOptional() {
        assertEquals(strategyService.findByName("NOT_EXISTED_STRATEGY"), Optional.empty());
    }

    @Test
    @DisplayName("When strategy exist then return optional strategy")
    public void whenStrategyExistThenReturnOptionalStrategy() {
        assertEquals(Optional.of(this.exampleStrategy), strategyService.findByName("EXAMPLE"));
    }
}