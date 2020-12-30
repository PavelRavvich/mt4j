package pro.laplacelab.mt4j.adapter;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
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

@SpringBootTest
@WebAppConfiguration
@RunWith(SpringRunner.class)
@DisplayName("Test ta4j rates mapper")
public class TAdapterTest {

    @Autowired
    Adapter<Duration, BarSeries> ta4jAdapter;

    @Test(expected = NullPointerException.class)
    @DisplayName("When rates is null then throw NullPointerException")
    public void whenRatesIsNullThenThrowNullPointerException() {
        ta4jAdapter.map(null);
    }

    @Test(expected = UnsupportedOperationException.class)
    @DisplayName("When try to use unsupported TBar#addTrade() then throw UnsupportedOperationException")
    public void whenCallUnsupportedAddTradeThenThrowUnsupportedOperationException() {
        TBar.builder()
                .timePeriod(Duration.ofMinutes(1))
                .endTime(ZonedDateTime.now())
                .build()
                .addTrade(DoubleNum.valueOf(1D), DoubleNum.valueOf(1D));
    }

    @Test(expected = UnsupportedOperationException.class)
    @DisplayName("When try to use unsupported TBar#addPrice() then throw UnsupportedOperationException")
    public void whenCallUnsupportedAddPriceThenThrowUnsupportedOperationException() {
        TBar.builder()
                .timePeriod(Duration.ofMinutes(1))
                .endTime(ZonedDateTime.now())
                .build()
                .addPrice(DoubleNum.valueOf(1D));
    }

    @Test
    @DisplayName("When rates successfully mapped one bar from MetaTrader5 to mt4j format")
    public void whenMapSuccessThenAllDataConvert() {
        // given
        final Map<Timeframe, List<Rate>> ratesMap = new HashMap<>(
                Map.of(Timeframe.M_1, List.of(Rate.builder().close(0.1D).low(0.1D).high(0.1D)
                        .open(0.1D).realVolume(1L).spread(1).tickVolume(1L)
                        .time(ZonedDateTime.now()).build())));

        final Rate rate = ratesMap.get(Timeframe.M_1).iterator().next();
        final TBar expected = TBar.builder()
                .volume(Double.valueOf(rate.getTickVolume()))
                .timePeriod(Duration.ofMinutes(1))
                .closePrice(rate.getClose())
                .openPrice(rate.getOpen())
                .highPrice(rate.getHigh())
                .lowPrice(rate.getLow())
                .endTime(rate.getTime())
                .spread(1)
                .build();

        // when
        final Map<Duration, BarSeries> map = ta4jAdapter.map(ratesMap);
        final TBar actualBar = (TBar) map.entrySet().iterator().next().getValue().getBar(0);

        // then
        Assertions.assertEquals(expected, actualBar);
        Assertions.assertEquals(Duration.ofMinutes(1), map.keySet().iterator().next());
        Assertions.assertEquals(expected.getVolume(), actualBar.getVolume());
        Assertions.assertEquals(expected.getTrades(), actualBar.getTrades());
        Assertions.assertEquals(expected.getAmount(), actualBar.getAmount());
        Assertions.assertEquals(expected.getEndTime(), actualBar.getEndTime());
        Assertions.assertEquals(expected.getEndTime(), actualBar.getEndTime());
        Assertions.assertEquals(expected.getLowPrice(), actualBar.getLowPrice());
        Assertions.assertEquals(expected.getOpenPrice(), actualBar.getOpenPrice());
        Assertions.assertEquals(expected.getHighPrice(), actualBar.getHighPrice());
        Assertions.assertEquals(expected.getBeginTime(), actualBar.getBeginTime());
        Assertions.assertEquals(expected.getClosePrice(), actualBar.getClosePrice());
        Assertions.assertEquals(expected.getTimePeriod(), actualBar.getTimePeriod());
    }
}