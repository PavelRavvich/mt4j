package pro.laplacelab.bridge.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import pro.laplacelab.bridge.enums.InputType;
import pro.laplacelab.bridge.enums.SignalType;
import pro.laplacelab.bridge.exception.AdvisorNotFoundException;
import pro.laplacelab.bridge.model.Advisor;
import pro.laplacelab.bridge.model.Input;
import pro.laplacelab.bridge.model.Position;
import pro.laplacelab.bridge.model.PositionTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@WebAppConfiguration
@RunWith(SpringRunner.class)
public class PositionServiceImplTest extends PositionTest {

    @Autowired
    PositionService positionService;

    @MockBean
    AdvisorService advisorService;

    @Test
    public void whenAddSuccessThenAddPositionMethodCalled() {
        final Advisor advisor = spy(new Advisor(1L, List.of(
                new Input("key1", "val", InputType.STRING))));
        final Position position = new Position(advisor.getId(), SignalType.BUY, positionId,
                lot, stopLoss, takeProfit, openAt, closeAt, profit);
        doReturn(Optional.of(advisor)).when(advisorService).get(position.getAdvisorId());
        positionService.add(position);

        verify(advisor, times(1)).addPosition(position);
        verify(advisorService, times(1)).get(advisor.getId());
    }

    @Test(expected = AdvisorNotFoundException.class)
    public void whenAddFailThenThrowAdvisorNotFoundException() {
        positionService.add(new Position(UUID.randomUUID(), SignalType.BUY, positionId,
                lot, stopLoss, takeProfit, openAt, closeAt, profit));
    }

    @Test
    public void whenUpdateSuccessThenUpdatePositionMethodCalled() {
        final Advisor advisor = spy(new Advisor(1L, List.of(
                new Input("key1", "val", InputType.STRING))));
        final Position origin = new Position(advisor.getId(), SignalType.BUY, positionId,
                lot, stopLoss, takeProfit, openAt, closeAt, profit);
        doReturn(Optional.of(advisor)).when(advisorService).get(origin.getAdvisorId());
        positionService.add(origin);
        final Position update = new Position(advisor.getId(), SignalType.BUY, positionId,
                new BigDecimal("0.1"), stopLoss, takeProfit, openAt, closeAt, profit);
        positionService.update(update);

        verify(advisor, times(1)).updatePosition(update);
        verify(advisorService, times(2)).get(advisor.getId());
        assertEquals(new BigDecimal("0.1"), advisor.getPositions().get(0).getLot());
    }

    @Test(expected = AdvisorNotFoundException.class)
    public void whenUpdateFailThenThrowAdvisorNotFoundException() {
        positionService.update(new Position(UUID.randomUUID(), SignalType.BUY, positionId,
                lot, stopLoss, takeProfit, openAt, closeAt, profit));
    }

    @Test
    public void whenHistorySuccessThenHistoryMethodCalled() {
        final Advisor advisor = spy(new Advisor(1L, List.of(
                new Input("key1", "val", InputType.STRING))));
        final Position position = new Position(advisor.getId(), SignalType.BUY, positionId,
                lot, stopLoss, takeProfit, openAt, closeAt, profit);
        doReturn(Optional.of(advisor)).when(advisorService).get(position.getAdvisorId());
        positionService.add(position);
        positionService.history(position);

        verify(advisor, times(1)).toHistory(position);
        verify(advisor, times(1)).updatePosition(position);
        verify(advisorService, times(2)).get(advisor.getId());
    }

    @Test(expected = AdvisorNotFoundException.class)
    public void whenHistoryFailThenThrowAdvisorNotFoundException() {
        positionService.history(new Position(UUID.randomUUID(), SignalType.BUY, positionId,
                lot, stopLoss, takeProfit, openAt, closeAt, profit));
    }

}