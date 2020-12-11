package pro.laplacelab.mt4j.adapter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBar;
import org.ta4j.core.num.DoubleNum;
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
public class AdapterTa4JTest {

    @Autowired
    Adapter<Duration, BarSeries> adapterTA4J;

    Map<Timeframe, List<Rate>> ratesMap = new HashMap<>(
            Map.of(Timeframe.M_1, List.of(Rate.builder().close(0.1D).low(0.1D).high(0.1D)
                                    .open(0.1D).realVolume(1L).spread(1).tickVolume(1L)
                                    .time(ZonedDateTime.now()).build())));

    @Test
    public void whenMapSuccessThenAllDataConvert() {
        final Map<Duration, BarSeries> map = adapterTA4J.map(ratesMap);
        final Rate rate = ratesMap.get(Timeframe.M_1).iterator().next();
        final BaseBar expectedBar = BaseBar
                .builder(DoubleNum::valueOf, Double.class)
                .volume(Double.valueOf(rate.getTickVolume()))
                .timePeriod(Duration.ofMinutes(1))
                .closePrice(rate.getClose())
                .openPrice(rate.getOpen())
                .highPrice(rate.getHigh())
                .lowPrice(rate.getLow())
                .endTime(rate.getTime())
                .build();
        final Bar actualBar = map.entrySet().iterator().next().getValue().getBar(0);
        final Duration actualDuration = map.keySet().iterator().next();

        assertEquals(expectedBar, actualBar);
        assertEquals(Duration.ofMinutes(1), actualDuration);
    }

    @Test(expected = NullPointerException.class)
    public void whenParamNullThenThrowNullPointerException() {
        adapterTA4J.map(null);
    }

}