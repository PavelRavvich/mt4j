package pro.laplacelab.mt4j.adapter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.num.DoubleNum;
import pro.laplacelab.mt4j.adapter.ta4j.TBar;
import pro.laplacelab.mt4j.enums.Timeframe;
import pro.laplacelab.mt4j.model.Rate;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@WebAppConfiguration
@RunWith(SpringRunner.class)
public class TAdapterTest {

    @Autowired
    Adapter<Duration, BarSeries> ta4jAdapter;

    Map<Timeframe, List<Rate>> ratesMap = new HashMap<>(
            Map.of(Timeframe.M_1, List.of(Rate.builder().close(0.1D).low(0.1D).high(0.1D)
                                    .open(0.1D).realVolume(1L).spread(1).tickVolume(1L)
                                    .time(ZonedDateTime.now()).build())));

    @Test(expected = NullPointerException.class)
    public void whenParamNullThenThrowNullPointerException() {
        ta4jAdapter.map(null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void whenCallAddTradeThenThrowUnsupportedOperationException() {
        TBar.builder()
                .timePeriod(Duration.ofMinutes(1))
                .endTime(ZonedDateTime.now())
                .build()
                .addTrade(DoubleNum.valueOf(1D), DoubleNum.valueOf(1D));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void whenCallAddPriceThenThrowUnsupportedOperationException() {
        TBar.builder()
                .timePeriod(Duration.ofMinutes(1))
                .endTime(ZonedDateTime.now())
                .build()
                .addPrice(DoubleNum.valueOf(1D));
    }

    @Test
    public void whenMapSuccessThenAllDataConvert() {
        final Map<Duration, BarSeries> map = ta4jAdapter.map(ratesMap);
        final Rate rate = ratesMap.get(Timeframe.M_1).iterator().next();
        TBar expected = TBar
                .builder()
                .volume(Double.valueOf(rate.getTickVolume()))
                .timePeriod(Duration.ofMinutes(1))
                .closePrice(rate.getClose())
                .openPrice(rate.getOpen())
                .highPrice(rate.getHigh())
                .lowPrice(rate.getLow())
                .endTime(rate.getTime())
                .spread(1)
                .build();

        final Bar actualBar = map.entrySet().iterator().next().getValue().getBar(0);
        final Duration actualDuration = map.keySet().iterator().next();

        assertEquals(expected, actualBar);
        assertEquals(Duration.ofMinutes(1), actualDuration);
        assertEquals(expected.getVolume(), actualBar.getVolume());
        assertEquals(expected.getTrades(), actualBar.getTrades());
        assertEquals(expected.getAmount(), actualBar.getAmount());
        assertEquals(expected.getEndTime(), actualBar.getEndTime());
        assertEquals(expected.getEndTime(), actualBar.getEndTime());
        assertEquals(expected.getLowPrice(), actualBar.getLowPrice());
        assertEquals(expected.getOpenPrice(), actualBar.getOpenPrice());
        assertEquals(expected.getHighPrice(), actualBar.getHighPrice());
        assertEquals(expected.getBeginTime(), actualBar.getBeginTime());
        assertEquals(expected.getClosePrice(), actualBar.getClosePrice());
        assertEquals(expected.getTimePeriod(), actualBar.getTimePeriod());
    }


}