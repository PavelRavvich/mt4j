package pro.laplacelab.mt4j.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import pro.laplacelab.mt4j.exception.DuplicateInputException;
import pro.laplacelab.mt4j.exception.DuplicatePositionException;
import pro.laplacelab.mt4j.exception.PositionNotFoundException;

import javax.validation.constraints.NotNull;
import java.util.*;

@Data
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class Advisor {

    @JsonProperty("id")
    private final UUID id;

    @JsonProperty("magic")
    private final Long magic;

    @JsonProperty("inputs")
    private final List<Input> inputs;

    @Getter
    @JsonIgnore
    private final LinkedList<Position> history = new LinkedList<>();

    @Getter
    @JsonIgnore
    private final LinkedList<Position> positions = new LinkedList<>();

    public Advisor(final @NotNull Long magic, final @NotNull List<Input> inputs) {
        if (inputs.size() != inputs.stream().distinct().count()) {
            throw new DuplicateInputException();
        }
        this.id = UUID.randomUUID();
        this.inputs = inputs;
        this.magic = magic;
    }

    public Optional<Input> getInput(final @NotNull String key) {
        return inputs.stream().filter(item -> key.equals(item.getKey())).findFirst();
    }

    public void addPosition(final @NotNull Position position) {
        if (positions.contains(position)) {
            throw new DuplicatePositionException();
        }
        positions.add(position);
    }

    public Optional<Position> findPositionById(final @NotNull Long positionId) {
        return positions.stream()
                .filter(item -> positionId.equals(item.getPositionId()))
                .findFirst();
    }

    public Optional<Position> findHistoryById(final @NotNull Long positionId) {
        return history.stream()
                .filter(item -> positionId.equals(item.getPositionId()))
                .findFirst();
    }

    public void toHistory(final @NotNull Position position) {
        if (!positions.contains(position)) {
            throw new PositionNotFoundException();
        }
        positions.stream()
                .filter(item -> item.getPositionId().equals(position.getPositionId()))
                .findFirst()
                .ifPresent(positions::remove);
        history.add(position);
    }

    public void updatePosition(final @NotNull Position position) {
        final Position candidate = positions.stream()
                .filter(item -> item.getPositionId().equals(position.getPositionId()))
                .findFirst().orElseThrow(PositionNotFoundException::new);
        candidate.setLot(position.getLot());
        candidate.setSwap(position.getSwap());
        candidate.setProfit(position.getProfit());
        candidate.setCloseAt(position.getCloseAt());
        candidate.setStopLoss(position.getStopLoss());
        candidate.setTakeProfit(position.getTakeProfit());
    }

    public int countDropdown() {
        int counter = 0;
        final Iterator<Position> iterator = history.descendingIterator();
        while (iterator.hasNext() && iterator.next().getProfit() < 0) {
            counter++;
        }
        return counter;
    }

}
