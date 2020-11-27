package pro.laplacelab.mt4j.service;

import org.junit.Test;
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
public class StrategyServiceImplTest {

    @Autowired
    StrategyService strategyService;

    @Autowired
    Strategy exampleStrategy;

    @Test
    public void whenStrategyNotFoundThenReturnEmptyOptional() {
        assertEquals(strategyService.findByName("NOT_EXISTED_STRATEGY"), Optional.empty());
    }

    @Test
    public void whenThen() {
        Optional<Strategy> exampleStrategy = strategyService.findByName("EXAMPLE");
        assertEquals(Optional.of(this.exampleStrategy), exampleStrategy);
    }
}