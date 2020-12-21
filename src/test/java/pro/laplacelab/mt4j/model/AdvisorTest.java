package pro.laplacelab.mt4j.model;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import pro.laplacelab.mt4j.BaseTestPreparation;
import pro.laplacelab.mt4j.enums.InputType;
import pro.laplacelab.mt4j.enums.PositionType;
import pro.laplacelab.mt4j.exception.DuplicateInputException;
import pro.laplacelab.mt4j.exception.DuplicatePositionException;
import pro.laplacelab.mt4j.exception.PositionNotFoundException;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@DisplayName("Advisor entity test")
public class AdvisorTest extends BaseTestPreparation {

    @Test
    @DisplayName("When advisor bailed successfully input data saved")
    public void whenAdvisorBuildSuccessfullyInputDataSaved() {
        // when
        final Advisor advisor = new Advisor(1L, List.of(
                new Input("key1", "val", InputType.STRING),
                new Input("key2", "1", InputType.NUMBER),
                new Input("key3", "10:00", InputType.DATETIME),
                new Input("key4", "true", InputType.BOOLEAN)));

        // then
        assertEquals(Long.valueOf(1), advisor.getMagic());
        assertEquals("val",
                advisor.getInput("key1").orElseThrow().asString());
        assertEquals(Double.valueOf("1"),
                advisor.getInput("key2").orElseThrow().asDouble());
        assertEquals(LocalTime.parse("10:00"),
                advisor.getInput("key3").orElseThrow().asLocalTime());
        assertEquals(true,
                advisor.getInput("key4").orElseThrow().asBoolean());
    }

    @Test
    @DisplayName("When add position successfully then findPositionById() return position")
    public void whenAddPositionSuccessThenFindPositionByIdReturnPosition() {
        // given
        final Input input = new Input("key1", "val", InputType.STRING);
        final Advisor advisor = new Advisor(1L, List.of(input));
        final Position position = new Position(PositionType.LONG, positionId,
                lot, stopLoss, takeProfit, openPrice, closePrice, openAt, closeAt, profit, swap, commission);
        advisor.addPosition(position);

        // when
        final Position expected = advisor.findPositionById(positionId).orElseThrow();

        // then
        assertEquals(position, expected);
    }

    @Test(expected = DuplicatePositionException.class)
    @DisplayName("When add already existed position twice then throw DuplicatePositionException")
    public void whenAddAlreadyExistedPositionTwiceThenThrowDuplicatePositionException() {
        // given
        final Input input = new Input("key1", "val", InputType.STRING);
        final Advisor advisor = new Advisor(1L, List.of(input));
        final Position position = new Position(PositionType.LONG, positionId,
                lot, stopLoss, takeProfit, openPrice, closePrice, openAt, closeAt, profit, swap, commission);

        // when
        advisor.addPosition(position);
        advisor.addPosition(position);
    }

    @Test
    @DisplayName("When existed position move to history then position in history")
    public void whenExistedPositionMoveToHistoryThenPositionInHistory() {
        // given
        final Input input = new Input("key1", "val", InputType.STRING);
        final Advisor advisor = new Advisor(1L, List.of(input));
        final Position position = new Position(PositionType.LONG, positionId,
                lot, stopLoss, takeProfit, openPrice, closePrice, openAt, closeAt, profit, swap, commission);
        advisor.addPosition(position);

        // when
        advisor.toHistory(position);
        final Optional<Position> origin = advisor.findPositionById(positionId);
        final Position history = advisor.findHistoryById(positionId).orElseThrow();

        // then
        assertTrue(origin.isEmpty());
        assertEquals(history, position);
    }

    @Test(expected = PositionNotFoundException.class)
    @DisplayName("When position move to history not exist then throw PositionNotFoundException")
    public void whenMoveToHistoryNotExistedPositionThenThrowPositionNotFoundException() {
        // given
        final Input input = new Input("key1", "val", InputType.STRING);
        final Advisor advisor = new Advisor(1L, List.of(input));
        final Position position = new Position(PositionType.LONG, positionId,
                lot, stopLoss, takeProfit, openPrice, closePrice, openAt, closeAt, profit, swap, commission);

        // when
        advisor.toHistory(position);
    }

    @Test
    @DisplayName("When update existed position then position successfully updated")
    public void whenUpdateExistedPositionThenPositionSuccessfullyUpdated() {
        // given
        final Input input = new Input("key1", "val", InputType.STRING);
        final Advisor advisor = new Advisor(1L, List.of(input));
        final Position origin = new Position(PositionType.LONG, positionId, lot, stopLoss,
                takeProfit, openPrice, closePrice, openAt, closeAt, profit, swap, commission);
        advisor.addPosition(origin);

        final Double newLot = 100d;
        final Double newSwap = 200d;
        final Double newProfit = 100d;
        final Integer newStopLoss = 1000;
        final Double newCommission = 100d;
        final Double newClosePrice = 100d;
        final Integer newTakeProfit = 1000;
        final Long newCloseAt = System.currentTimeMillis();

        final Position forUpdate = new Position(
                PositionType.LONG, positionId, newLot, newStopLoss, newTakeProfit,
                openPrice, newClosePrice, openAt, newCloseAt, newProfit, newSwap, newCommission);
        origin.setProfit(100d);

        // when
        advisor.updatePosition(forUpdate);
        final Position expected = advisor.findPositionById(positionId).orElseThrow();

        // then
        assertEquals(newLot, expected.getLot());
        assertEquals(newSwap, expected.getSwap());
        assertEquals(newProfit, expected.getProfit());
        assertEquals(newCloseAt, expected.getCloseAt());
        assertEquals(newStopLoss, expected.getStopLoss());
        assertEquals(newCommission, expected.getCommission());
        assertEquals(newClosePrice, expected.getClosePrice());
        assertEquals(newTakeProfit, expected.getTakeProfit());
    }

    @Test(expected = PositionNotFoundException.class)
    @DisplayName("When update not existed position then throw PositionNotFoundException")
    public void whenUpdateNotExistedPositionThenThrowPositionNotFoundException() {
        // given
        final Input input = new Input("key1", "val", InputType.STRING);
        final Advisor advisor = new Advisor(1L, List.of(input));
        final Position origin = new Position(PositionType.LONG, positionId,
                lot, stopLoss, takeProfit, openPrice, closePrice, openAt, closeAt, profit, swap, commission);

        // when
        advisor.updatePosition(origin);
    }

    @Test
    @DisplayName("When count not exist dropdown then return zero")
    public void whenCountNotExistDropdownThenReturnZero() {
        // given
        final Input input = new Input("key1", "val", InputType.STRING);
        final Advisor advisor = new Advisor(1L, List.of(input));
        final Position dropdown = new Position(PositionType.LONG, positionId,
                lot, stopLoss, takeProfit, openPrice, closePrice, openAt, closeAt, -1D, swap, commission);
        final Position profit = new Position(PositionType.LONG, 2L,
                lot, stopLoss, takeProfit, openPrice, closePrice, openAt, closeAt, this.profit, swap, commission);
        advisor.addPosition(dropdown);
        advisor.addPosition(profit);

        // when
        advisor.toHistory(dropdown);
        advisor.toHistory(profit);

        // then
        assertEquals(0, advisor.countDropdown());
    }

    @Test
    @DisplayName("When dropdown position move to history then dropdown counter return one")
    public void whenDropdownPositionMoveToHistoryThenDropdownCounterReturnOne() {
        // given
        final Input input = new Input("key1", "val", InputType.STRING);
        final Advisor advisor = new Advisor(1L, List.of(input));
        final Position position = new Position(PositionType.LONG, positionId,
                lot, stopLoss, takeProfit, openPrice, closePrice, openAt, closeAt, -1d, swap, commission);
        advisor.addPosition(position);

        // when
        advisor.toHistory(position);

        // then
        assertEquals(1, advisor.countDropdown());
    }

    @Test(expected = DuplicateInputException.class)
    @DisplayName("When input keys duplicated then throw DuplicateInputException")
    public void whenInputKeysDuplicatedThenThrowDuplicateInputException() {
        // when
        final Input input = new Input("key1", "val", InputType.STRING);
        final Input duplicate = new Input("key1", "val", InputType.STRING);

        // then
        new Advisor(1L, List.of(input, duplicate));
    }
}