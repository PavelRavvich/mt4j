package pro.laplacelab.mt4j.model;

import org.junit.Test;
import pro.laplacelab.mt4j.BaseTestPreparation;
import pro.laplacelab.mt4j.enums.InputType;
import pro.laplacelab.mt4j.enums.PositionType;
import pro.laplacelab.mt4j.exception.DuplicateInputException;
import pro.laplacelab.mt4j.exception.DuplicatePositionException;
import pro.laplacelab.mt4j.exception.PositionNotFoundException;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AdvisorTest extends BaseTestPreparation {

    @Test
    public void whenAdvisorBuildSuccessfullyDataSaved() {
        final Advisor advisor = new Advisor(1L, List.of(
                new Input("key1", "val", InputType.STRING),
                new Input("key2", "1", InputType.NUMBER),
                new Input("key3", "10:00", InputType.DATETIME)));
        assertEquals(Long.valueOf(1), advisor.getMagic());
        assertEquals("val",
                advisor.getInput("key1").orElseThrow().asString());
        assertEquals(new BigDecimal("1"),
                advisor.getInput("key2").orElseThrow().asBigDecimal());
        assertEquals(LocalTime.parse("10:00"),
                advisor.getInput("key3").orElseThrow().asLocalTime());

    }


    @Test(expected = RuntimeException.class)
    public void whenAdvisorBuildFailThenThrowRuntimeException() {
        new Input("", "val", InputType.STRING);
    }

    @Test
    public void whenAddPositionSuccessThenFindPositionByIdReturnPosition() {
        final Input input = new Input("key1", "val", InputType.STRING);
        final Advisor advisor = new Advisor(1L, List.of(input));
        final Position position = new Position(PositionType.LONG, positionId,
                lot, stopLoss, takeProfit, openAt, closeAt, profit, swap);
        advisor.addPosition(position);
        final Position expected = advisor.findPositionById(positionId).orElseThrow();
        assertEquals(position, expected);
    }

    @Test(expected = DuplicatePositionException.class)
    public void whenAddAlreadyExistedPositionTwiceThenThrowDuplicatePositionException() {
        final Input input = new Input("key1", "val", InputType.STRING);
        final Advisor advisor = new Advisor(1L, List.of(input));
        final Position position = new Position(PositionType.LONG, positionId,
                lot, stopLoss, takeProfit, openAt, closeAt, profit, swap);
        advisor.addPosition(position);
        advisor.addPosition(position);
    }

    @Test
    public void whenExistedPositionMoveToHistoryThenPositionInHistory() {
        final Input input = new Input("key1", "val", InputType.STRING);
        final Advisor advisor = new Advisor(1L, List.of(input));
        final Position position = new Position(PositionType.LONG, positionId,
                lot, stopLoss, takeProfit, openAt, closeAt, profit, swap);
        advisor.addPosition(position);
        advisor.toHistory(position);
        final Optional<Position> origin = advisor.findPositionById(positionId);
        final Position history = advisor.findHistoryById(positionId).orElseThrow();
        assertTrue(origin.isEmpty());
        assertEquals(history, position);
    }

    @Test(expected = PositionNotFoundException.class)
    public void whenMoveToHistoryNotExistedPositionThenThrowPositionNotFoundException() {
        final Input input = new Input("key1", "val", InputType.STRING);
        final Advisor advisor = new Advisor(1L, List.of(input));
        final Position position = new Position(PositionType.LONG, positionId,
                lot, stopLoss, takeProfit, openAt, closeAt, profit, swap);
        advisor.toHistory(position);
    }

    @Test
    public void whenUpdateExistedPositionThenPositionSuccessUpdated() {
        final Input input = new Input("key1", "val", InputType.STRING);
        final Advisor advisor = new Advisor(1L, List.of(input));
        final Position origin = new Position(PositionType.LONG, positionId,
                lot, stopLoss, takeProfit, openAt, closeAt, profit, swap);
        advisor.addPosition(origin);

        final Double newLot = 100d;
        final Double newSwap = 200d;
        final Double newProfit = 100d;
        final Integer newStopLoss = 1000;
        final Integer newTakeProfit = 1000;
        final Long newCloseAt = System.currentTimeMillis();

        final Position forUpdate = new Position(PositionType.LONG, positionId,
                newLot, newStopLoss, newTakeProfit, openAt, newCloseAt, newProfit, newSwap);
        origin.setProfit(100d);
        advisor.updatePosition(forUpdate);
        final Position expected = advisor.findPositionById(positionId).orElseThrow();

        assertEquals(newLot, expected.getLot());
        assertEquals(newSwap, expected.getSwap());
        assertEquals(newProfit, expected.getProfit());
        assertEquals(newCloseAt, expected.getCloseAt());
        assertEquals(newStopLoss, expected.getStopLoss());
        assertEquals(newTakeProfit, expected.getTakeProfit());
    }

    @Test(expected = PositionNotFoundException.class)
    public void whenUpdatePositionWhichNotExistedThenThrowPositionNotFoundException() {
        final Input input = new Input("key1", "val", InputType.STRING);
        final Advisor advisor = new Advisor(1L, List.of(input));
        final Position origin = new Position(PositionType.LONG, positionId,
                lot, stopLoss, takeProfit, openAt, closeAt, profit, swap);
        advisor.updatePosition(origin);
    }

    @Test
    public void whenCountNotExistDropdownThenReturnZero() {
        final Input input = new Input("key1", "val", InputType.STRING);
        final Advisor advisor = new Advisor(1L, List.of(input));
        final Position dropdown = new Position(PositionType.LONG, positionId,
                lot, stopLoss, takeProfit, openAt, closeAt, -1D, swap);
        final Position profit = new Position(PositionType.LONG, 2L,
                lot, stopLoss, takeProfit, openAt, closeAt, this.profit, swap);
        advisor.addPosition(dropdown);
        advisor.addPosition(profit);
        advisor.toHistory(dropdown);
        advisor.toHistory(profit);
        assertEquals(0, advisor.countDropdown());
    }

    @Test
    public void whenDropdownPositionMoveToHistoryThenDropdownCounterReturnOne() {
        final Input input = new Input("key1", "val", InputType.STRING);
        final Advisor advisor = new Advisor(1L, List.of(input));
        final Position position = new Position(PositionType.LONG, positionId,
                lot, stopLoss, takeProfit, openAt, closeAt, -1d, swap);
        advisor.addPosition(position);
        advisor.toHistory(position);
        assertEquals(1, advisor.countDropdown());
    }

    @Test(expected = DuplicateInputException.class)
    public void whenInputKeysDuplicatedThenThrowDuplicateInputException() {
        final Input input = new Input("key1", "val", InputType.STRING);
        final Input duplicate = new Input("key1", "val", InputType.STRING);
        new Advisor(1L, List.of(input, duplicate));
    }

}