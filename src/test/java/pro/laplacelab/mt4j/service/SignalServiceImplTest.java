package pro.laplacelab.mt4j.service;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import pro.laplacelab.mt4j.BaseTestPreparation;
import pro.laplacelab.mt4j.enums.InputType;
import pro.laplacelab.mt4j.enums.SignalType;
import pro.laplacelab.mt4j.exception.AdvisorNotFoundException;
import pro.laplacelab.mt4j.exception.StrategyNotFoundException;
import pro.laplacelab.mt4j.model.*;
import pro.laplacelab.mt4j.strategy.Example;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


@SpringBootTest
@WebAppConfiguration
@RunWith(SpringRunner.class)
@DisplayName("SignalService test")
public class SignalServiceImplTest extends BaseTestPreparation {

    @Autowired
    SignalService signalService;

    @MockBean
    AdvisorService advisorService;

    @MockBean
    StrategyService strategyService;

    @Test
    @DisplayName("When Signal generated successfully then services called")
    public void whenSignalGeneratedSuccessThenServicesCalled() {
        // given
        final Advisor advisor = spy(new Advisor(1L, List.of(
                new Input("key1", "val", InputType.STRING))));
        final List<Signal> signals = List.of(
                new Signal(advisorId, SignalType.BUY, lot, stopLoss, takeProfit));
        final Market market = new Market(advisor.getId(), new Account(),
                "EXAMPLE", new ArrayList<>(), new HashMap<>());
        final Example example = mock(Example.class);

        when(advisorService.findByAdvisorId(advisor.getId())).thenReturn(Optional.of(advisor));
        when(strategyService.findByName("EXAMPLE")).thenReturn(Optional.of(example));
        when(example.apply(advisor, market.getRates())).thenReturn(signals);

        // when
        final List<Signal> result = signalService.onTick(market);

        // then
        verify(advisorService, times(1)).findByAdvisorId(advisor.getId());
        verify(strategyService, times(1)).findByName(market.getStrategyName());
        assertEquals(signals, result);
    }

    @Test(expected = AdvisorNotFoundException.class)
    @DisplayName("When Advisor not exist then throw AdvisorNotFoundException")
    public void whenAdvisorNotExistThenThrowAdvisorNotFoundException() {
        signalService.onTick(new Market(UUID.randomUUID(), new Account(),
                "EXAMPLE", new ArrayList<>(), new HashMap<>()));
    }

    @Test(expected = StrategyNotFoundException.class)
    @DisplayName("When Strategy not exist then throw StrategyNotFoundException")
    public void whenStrategyNotExistThenThrowStrategyNotFoundException() {
        // given
        final Advisor advisor = spy(new Advisor(1L, List.of(
                new Input("key1", "val", InputType.STRING))));

        // when
        when(advisorService.findByAdvisorId(advisor.getId())).thenReturn(Optional.of(advisor));

        //then
        signalService.onTick(new Market(advisor.getId(), new Account(),
                "NOT_EXIST_STRATEGY", new ArrayList<>(), new HashMap<>()));
    }
}