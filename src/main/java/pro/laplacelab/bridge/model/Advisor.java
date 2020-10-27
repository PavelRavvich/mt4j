package pro.laplacelab.bridge.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import pro.laplacelab.bridge.exception.DuplicateInputException;
import pro.laplacelab.bridge.exception.PositionNotFoundException;

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
    private final transient LinkedList<Position> history = new LinkedList<>();

    @Getter
    @JsonIgnore
    private final transient LinkedList<Position> positions = new LinkedList<>();

    public Advisor(final @NotNull Long magic, final @NotNull List<Input> inputs) {
        if (inputs.size() != inputs.stream().map(Input::getKey).count()) {
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
        positions.add(position);
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
        if (history.size() == 1_000) {
            final List<Position> swap = new ArrayList<>(100);
            final Iterator<Position> iterator = history.descendingIterator();
            int copy = 0;
            while (copy++ != 100) {
                swap.add(iterator.next());
            }
            history.clear();
            history.addAll(swap);
        }
    }

    public void updatePosition(final @NotNull Position position) {
        final Position candidate = positions.stream()
                .filter(item -> item.getPositionId().equals(position.getPositionId()))
                .findFirst().orElseThrow();
        candidate.setLot(position.getLot());
        candidate.setProfit(position.getProfit());
        candidate.setStopLoss(position.getStopLoss());
        candidate.setTakeProfit(position.getTakeProfit());
    }

    public int countDropdown() {
        int counter = 0;
        final Iterator<Position> iterator = history.descendingIterator();
        while (iterator.hasNext() && iterator.next().getProfit().signum() < 0) {
            counter++;
        }
        return counter;
    }

}
