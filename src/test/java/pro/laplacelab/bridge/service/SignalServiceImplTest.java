package pro.laplacelab.bridge.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import pro.laplacelab.bridge.BaseTestPreparation;
import pro.laplacelab.bridge.enums.InputType;
import pro.laplacelab.bridge.enums.SignalType;
import pro.laplacelab.bridge.example.Example;
import pro.laplacelab.bridge.exception.AdvisorNotFoundException;
import pro.laplacelab.bridge.exception.StrategyNotFoundException;
import pro.laplacelab.bridge.model.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


@SpringBootTest
@WebAppConfiguration
@RunWith(SpringRunner.class)
public class SignalServiceImplTest extends BaseTestPreparation {

    @Autowired
    SignalService signalService;

    @MockBean
    AdvisorService advisorService;

    @MockBean
    StrategyService strategyService;

    @Test
    public void whenSignalGeneratedSuccessThenServicesCalled() {
        final Advisor advisor = spy(new Advisor(1L, List.of(
                new Input("key1", "val", InputType.STRING))));
        final Position position = new Position(advisor.getId(), SignalType.BUY, positionId,
                lot, stopLoss, takeProfit, openAt, closeAt, profit);
        final Signal signal = new Signal(advisor.getId(), SignalType.NO_ACTION);
        final Market market = new Market(advisor.getId(), "EXAMPLE", new HashMap<>());
        final Example example = mock(Example.class);

        doReturn(Optional.of(advisor)).when(advisorService).get(position.getAdvisorId());
        doReturn(Optional.of(example)).when(strategyService).findByName("EXAMPLE");
        doReturn(signal).when(example).apply(advisor, market.getRates());

        final Signal result = signalService.onTick(market);
        verify(advisorService, times(1)).get(advisor.getId());
        verify(strategyService, times(1)).findByName(market.getStrategyName());
        assertEquals(signal, result);
    }

    @Test(expected = AdvisorNotFoundException.class)
    public void whenAdvisorNotExistThenThrowAdvisorNotFoundException() {
        signalService.onTick(new Market(UUID.randomUUID(), "EXAMPLE", new HashMap<>()));
    }

    @Test(expected = StrategyNotFoundException.class)
    public void whenStrategyNotExistThenThrowStrategyNotFoundException() {
        final Advisor advisor = spy(new Advisor(1L, List.of(
                new Input("key1", "val", InputType.STRING))));
        final Position position = new Position(advisor.getId(), SignalType.BUY, positionId,
                lot, stopLoss, takeProfit, openAt, closeAt, profit);

        doReturn(Optional.of(advisor)).when(advisorService).get(position.getAdvisorId());

        signalService.onTick(new Market(advisor.getId(), "EXAMPLE", new HashMap<>()));
    }

}