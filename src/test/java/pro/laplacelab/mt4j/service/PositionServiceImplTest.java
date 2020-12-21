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
@DisplayName("PositionService test")
public class PositionServiceImplTest extends BaseTestPreparation {

    @Autowired
    PositionService positionService;

    @MockBean
    AdvisorService advisorService;

    @Test
    @DisplayName("When add() successfully then Advisor#addPosition methods called and return added position")
    public void whenAddSuccessfullyThenAddPositionMethodCalledAndReturnAddedPosition() {
        // given
        final Advisor advisor = spy(new Advisor(1L, List.of(
                new Input("key1", "val", InputType.STRING))));
        final Position position = new Position(PositionType.LONG, positionId, lot, stopLoss,
                takeProfit, openPrice, closePrice, openAt, closeAt, profit, swap, commission);
        when(advisorService.findByAdvisorId(advisor.getId())).thenReturn(Optional.of(advisor));

        // when
        final Position addedPosition = positionService.add(advisor.getId(), position);

        // then
        assertEquals(position, addedPosition);
        verify(advisor, times(1)).addPosition(position);
        verify(advisorService, times(1)).findByAdvisorId(advisor.getId());
    }

    @Test(expected = AdvisorNotFoundException.class)
    @DisplayName("When add() fail then throw AdvisorNotFoundException")
    public void whenAddFailThenThrowAdvisorNotFoundException() {
        positionService.add(advisorId, new Position(PositionType.LONG, positionId, lot, stopLoss,
                takeProfit, openPrice, closePrice, openAt, closeAt, profit, swap, commission));
    }

    @Test
    @DisplayName("When update success then Advisor#updatePosition() method called and position updated")
    public void whenUpdateSuccessThenUpdatePositionMethodCalledAndPositionUpdated() {
        // given
        final Advisor advisor = spy(new Advisor(1L, List.of(
                new Input("key1", "val", InputType.STRING))));
        final Position origin = new Position(PositionType.LONG, positionId, lot, stopLoss,
                takeProfit, openPrice, closePrice, openAt, closeAt, profit, swap, commission);
        when(advisorService.findByAdvisorId(advisor.getId())).thenReturn(Optional.of(advisor));
        positionService.add(advisor.getId(), origin);
        final Position update = new Position(PositionType.LONG, positionId, 0.2, stopLoss, takeProfit,
                openPrice, closePrice, openAt, closeAt, profit, swap, commission);

        // when
        final Position updatedPosition = positionService.update(advisor.getId(), update);

        // then
        assertEquals(update, updatedPosition);
        verify(advisor, times(1)).updatePosition(update);
        verify(advisorService, times(2)).findByAdvisorId(advisor.getId());
    }

    @Test(expected = AdvisorNotFoundException.class)
    @DisplayName("When update fail then throw AdvisorNotFoundException")
    public void whenUpdateFailThenThrowAdvisorNotFoundException() {
        positionService.update(advisorId, new Position(PositionType.LONG, positionId, lot, stopLoss,
                takeProfit, openPrice, closePrice, openAt, closeAt, profit, swap, commission));
    }

    @Test
    @DisplayName("When move to history successfully then Advisor#toHistory() called and position moved to history")
    public void whenHistorySuccessThenToHistoryCalledAndPositionMovedToHistory() {
        // given
        final Advisor advisor = spy(new Advisor(1L, List.of(
                new Input("key1", "val", InputType.STRING))));
        final Position position = new Position(PositionType.LONG, positionId, lot, stopLoss,
                takeProfit, openPrice, closePrice, openAt, closeAt, profit, swap, commission);
        when(advisorService.findByAdvisorId(advisor.getId())).thenReturn(Optional.of(advisor));
        positionService.add(advisor.getId(), position);

        // when
        final Position historizedPosition = positionService.history(advisor.getId(), position);

        // then
        assertEquals(historizedPosition, position);
        verify(advisor, times(1)).toHistory(position);
        verify(advisor, times(1)).updatePosition(position);
        verify(advisorService, times(2)).findByAdvisorId(advisor.getId());
    }

    @Test(expected = AdvisorNotFoundException.class)
    @DisplayName("When history fail then throw AdvisorNotFoundException")
    public void whenHistoryFailThenThrowAdvisorNotFoundException() {
        positionService.history(advisorId, new Position(PositionType.LONG, positionId, lot, stopLoss,
                takeProfit, openPrice, closePrice, openAt, closeAt, profit, swap, commission));
    }
}