package pro.laplacelab.mt4j.adapter.ta4j;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

import java.time.Duration;
import java.time.ZonedDateTime;

@DisplayName("Test bars comparing")
public class TBarComparatorTest {

    @Test
    @DisplayName("When compare bars with TBarComparator then result the same compare open prices")
    public void whenCompareTBarsCalledResultEqualsCompareToOpenPrice() {
        // given
        final TBarComparator tBarComparator = new TBarComparator();
        final ZonedDateTime dateTime = ZonedDateTime.now();
        final Duration duration = Duration.ofDays(1);
        final TBar bar1 = TBar.builder().openPrice(0.1).endTime(dateTime).timePeriod(duration).build();
        final TBar bar2 = TBar.builder().openPrice(0.1).endTime(dateTime).timePeriod(duration).build();
        final int expected = bar1.getOpenPrice().compareTo(bar2.getOpenPrice());

        // when
        final int actual = tBarComparator.compare(bar1, bar2);

        // then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("When compare bars then result the same compare open prices")
    public void whenCompareToTBarsCalledResultEqualsCompareToOpenPrice() {
        // given
        final ZonedDateTime dateTime = ZonedDateTime.now();
        final Duration duration = Duration.ofDays(1);
        final TBar bar1 = TBar.builder().openPrice(0.1).endTime(dateTime).timePeriod(duration).build();
        final TBar bar2 = TBar.builder().openPrice(0.1).endTime(dateTime).timePeriod(duration).build();
        final int expected = bar1.getOpenPrice().compareTo(bar2.getOpenPrice());

        // when
        final int actual = bar1.compareTo(bar2);

        // then
        Assertions.assertEquals(expected, actual);
    }
}