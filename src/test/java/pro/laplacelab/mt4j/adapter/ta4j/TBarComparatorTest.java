package pro.laplacelab.mt4j.adapter.ta4j;

import org.junit.Test;

import java.time.Duration;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TBarComparatorTest {

    @Test
    public void whenCompareTBarsCalledResultEqualsCompareToOpenPrice() {
        final TBarComparator tBarComparator = new TBarComparator();
        final ZonedDateTime dateTime = ZonedDateTime.now();
        final Duration duration = Duration.ofDays(1);
        final TBar bar1 = TBar.builder().openPrice(0.1).endTime(dateTime).timePeriod(duration).build();
        final TBar bar2 = TBar.builder().openPrice(0.1).endTime(dateTime).timePeriod(duration).build();
        final int expected = bar1.getOpenPrice().compareTo(bar2.getOpenPrice());

        final int actual = tBarComparator.compare(bar1, bar2);
        assertEquals(expected,actual);
    }

    @Test
    public void whenCompareToTBarsCalledResultEqualsCompareToOpenPrice() {
        final ZonedDateTime dateTime = ZonedDateTime.now();
        final Duration duration = Duration.ofDays(1);
        final TBar bar1 = TBar.builder().openPrice(0.1).endTime(dateTime).timePeriod(duration).build();
        final TBar bar2 = TBar.builder().openPrice(0.1).endTime(dateTime).timePeriod(duration).build();
        final int expected = bar1.getOpenPrice().compareTo(bar2.getOpenPrice());

        final int actual = bar1.compareTo(bar2);
        assertEquals(expected,actual);
    }
}