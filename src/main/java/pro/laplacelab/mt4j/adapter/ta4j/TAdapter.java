package pro.laplacelab.mt4j.adapter.ta4j;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBar;
import org.ta4j.core.BaseBarSeries;
import org.ta4j.core.num.DoubleNum;
import pro.laplacelab.mt4j.adapter.Adapter;
import pro.laplacelab.mt4j.enums.Timeframe;
import pro.laplacelab.mt4j.model.Rate;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TAdapter implements Adapter<Duration, BarSeries> {

    private final Map<Timeframe, Duration> timeframeMap = new HashMap<>();

    @PostConstruct
    public void init() {
        timeframeMap.put(Timeframe.M_1, Duration.ofMinutes(1));
        timeframeMap.put(Timeframe.M_2, Duration.ofMinutes(2));
        timeframeMap.put(Timeframe.M_3, Duration.ofMinutes(3));
        timeframeMap.put(Timeframe.M_4, Duration.ofMinutes(4));
        timeframeMap.put(Timeframe.M_5, Duration.ofMinutes(5));
        timeframeMap.put(Timeframe.M_6, Duration.ofMinutes(6));
        timeframeMap.put(Timeframe.M_10, Duration.ofMinutes(10));
        timeframeMap.put(Timeframe.M_12, Duration.ofMinutes(12));
        timeframeMap.put(Timeframe.M_15, Duration.ofMinutes(15));
        timeframeMap.put(Timeframe.M_20, Duration.ofMinutes(20));
        timeframeMap.put(Timeframe.M_30, Duration.ofMinutes(30));
        timeframeMap.put(Timeframe.H_1, Duration.ofHours(1));
        timeframeMap.put(Timeframe.H_2, Duration.ofHours(2));
        timeframeMap.put(Timeframe.H_3, Duration.ofHours(3));
        timeframeMap.put(Timeframe.H_4, Duration.ofHours(4));
        timeframeMap.put(Timeframe.H_6, Duration.ofHours(6));
        timeframeMap.put(Timeframe.H_8, Duration.ofHours(8));
        timeframeMap.put(Timeframe.H_12, Duration.ofHours(12));
        timeframeMap.put(Timeframe.D_1, Duration.ofDays(1));
        timeframeMap.put(Timeframe.W_1, Duration.ofDays(5));
        timeframeMap.put(Timeframe.MN_1, Duration.ofDays(20));
    }

    @Override
    public Map<Duration, BarSeries> map(final @NonNull Map<Timeframe, List<Rate>> ratesMap) {
        final Map<Duration, BarSeries> barSeries = new HashMap<>(21);
        for (Timeframe timeframe : ratesMap.keySet()) {
            final Duration duration = timeframeMap.get(timeframe);
            final List<Rate> originRates = ratesMap.get(timeframe);
            final BarSeries itemBarSeries = toBarSeries(timeframe, originRates);
            barSeries.put(duration, itemBarSeries);
        }
        return barSeries;
    }

    private BarSeries toBarSeries(final Timeframe timeframe, final List<Rate> rates) {
        final Duration duration = timeframeMap.get(timeframe);
        final List<Bar> bars = new ArrayList<>(20);
        for (Rate rate : rates) {
            bars.add(TBar.builder()
                    .spread(rate.getSpread())
                    .baseBar(BaseBar
                            .builder(DoubleNum::valueOf, Double.class)
                            .volume(Double.valueOf(rate.getTickVolume()))
                            .closePrice(rate.getClose())
                            .openPrice(rate.getOpen())
                            .highPrice(rate.getHigh())
                            .lowPrice(rate.getLow())
                            .endTime(rate.getTime())
                            .timePeriod(duration)
                            .build())
                    .build()
            );
        }
        return new BaseBarSeries(bars);
    }

}
