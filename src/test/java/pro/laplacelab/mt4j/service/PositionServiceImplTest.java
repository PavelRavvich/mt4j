package pro.laplacelab.mt4j.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import pro.laplacelab.mt4j.BaseTestPreparation;
import pro.laplacelab.mt4j.enums.InputType;
import pro.laplacelab.mt4j.enums.PositionType;
import pro.laplacelab.mt4j.exception.AdvisorNotFoundException;
import pro.laplacelab.mt4j.model.Advisor;
import pro.laplacelab.mt4j.model.Input;
import pro.laplacelab.mt4j.model.Position;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@WebAppConfiguration
@RunWith(SpringRunner.class)
public class PositionServiceImplTest extends BaseTestPreparation {

    @Autowired
    PositionService positionService;

    @MockBean
    AdvisorService advisorService;

    @Test
    public void whenAddSuccessThenAddPositionMethodCalled() {
        final Advisor advisor = spy(new Advisor(1L, List.of(
                new Input("key1", "val", InputType.STRING))));
        final Position position = new Position(PositionType.LONG, positionId,
                lot, stopLoss, takeProfit, openAt, closeAt, profit);
        when(advisorService.get(advisor.getId())).thenReturn(Optional.of(advisor));
        positionService.add(advisor.getId(), position);

        verify(advisor, times(1)).addPosition(position);
        verify(advisorService, times(1)).get(advisor.getId());
    }

    @Test(expected = AdvisorNotFoundException.class)
    public void whenAddFailThenThrowAdvisorNotFoundException() {
        positionService.add(advisorId, new Position(PositionType.LONG, positionId,
                lot, stopLoss, takeProfit, openAt, closeAt, profit));
    }

    @Test
    public void whenUpdateSuccessThenUpdatePositionMethodCalled() {
        final Advisor advisor = spy(new Advisor(1L, List.of(
                new Input("key1", "val", InputType.STRING))));
        final Position origin = new Position(PositionType.LONG, positionId,
                lot, stopLoss, takeProfit, openAt, closeAt, profit);

        when(advisorService.get(advisor.getId())).thenReturn(Optional.of(advisor));
        positionService.add(advisor.getId(), origin);
        final Position update = new Position(PositionType.LONG, positionId,
                0.1, stopLoss, takeProfit, openAt, closeAt, profit);
        positionService.update(advisor.getId(), update);

        verify(advisor, times(1)).updatePosition(update);
        verify(advisorService, times(2)).get(advisor.getId());
        assertEquals(Double.valueOf(0.1), advisor.getPositions().get(0).getLot());
    }

    @Test(expected = AdvisorNotFoundException.class)
    public void whenUpdateFailThenThrowAdvisorNotFoundException() {
        positionService.update(advisorId, new Position(PositionType.LONG,
                positionId, lot, stopLoss, takeProfit, openAt, closeAt, profit));
    }

    @Test
    public void whenHistorySuccessThenHistoryMethodCalled() {
        final Advisor advisor = spy(new Advisor(1L, List.of(
                new Input("key1", "val", InputType.STRING))));
        final Position position = new Position(PositionType.LONG, positionId,
                lot, stopLoss, takeProfit, openAt, closeAt, profit);
        when(advisorService.get(advisor.getId())).thenReturn(Optional.of(advisor));
        positionService.add(advisor.getId(), position);
        positionService.history(advisor.getId(), position);

        verify(advisor, times(1)).toHistory(position);
        verify(advisor, times(1)).updatePosition(position);
        verify(advisorService, times(2)).get(advisor.getId());
    }

    @Test(expected = AdvisorNotFoundException.class)
    public void whenHistoryFailThenThrowAdvisorNotFoundException() {
        positionService.history(advisorId, new Position(PositionType.LONG, positionId,
                lot, stopLoss, takeProfit, openAt, closeAt, profit));
    }

}