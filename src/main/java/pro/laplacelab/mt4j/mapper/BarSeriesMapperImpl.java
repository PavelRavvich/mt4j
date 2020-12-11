package pro.laplacelab.mt4j.mapper;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBar;
import org.ta4j.core.BaseBarSeriesBuilder;
import org.ta4j.core.num.DoubleNum;
import pro.laplacelab.mt4j.enums.Timeframe;
import pro.laplacelab.mt4j.model.Rate;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BarSeriesMapperImpl implements BarSeriesMapper {

    private final Map<Timeframe, Duration> timeframeDurationMap = new HashMap<>();

    @PostConstruct
    public void init() {
        timeframeDurationMap.put(Timeframe.M_1, Duration.ofMinutes(1));
        timeframeDurationMap.put(Timeframe.M_2, Duration.ofMinutes(2));
        timeframeDurationMap.put(Timeframe.M_3, Duration.ofMinutes(3));
        timeframeDurationMap.put(Timeframe.M_4, Duration.ofMinutes(4));
        timeframeDurationMap.put(Timeframe.M_5, Duration.ofMinutes(5));
        timeframeDurationMap.put(Timeframe.M_6, Duration.ofMinutes(6));
        timeframeDurationMap.put(Timeframe.M_10, Duration.ofMinutes(10));
        timeframeDurationMap.put(Timeframe.M_12, Duration.ofMinutes(12));
        timeframeDurationMap.put(Timeframe.M_15, Duration.ofMinutes(15));
        timeframeDurationMap.put(Timeframe.M_20, Duration.ofMinutes(20));
        timeframeDurationMap.put(Timeframe.M_30, Duration.ofMinutes(30));
        timeframeDurationMap.put(Timeframe.H_1, Duration.ofHours(1));
        timeframeDurationMap.put(Timeframe.H_2, Duration.ofHours(2));
        timeframeDurationMap.put(Timeframe.H_3, Duration.ofHours(3));
        timeframeDurationMap.put(Timeframe.H_4, Duration.ofHours(4));
        timeframeDurationMap.put(Timeframe.H_6, Duration.ofHours(6));
        timeframeDurationMap.put(Timeframe.H_8, Duration.ofHours(8));
        timeframeDurationMap.put(Timeframe.H_12, Duration.ofHours(12));
        timeframeDurationMap.put(Timeframe.D_1, Duration.ofDays(1));
        timeframeDurationMap.put(Timeframe.W_1, Duration.ofDays(5));
        timeframeDurationMap.put(Timeframe.MN_1, Duration.ofDays(20));

    }

    public Map<Duration, BarSeries> toBarSeries(final @NonNull Map<Timeframe, List<Rate>> rates) {
        final Map<Duration, BarSeries> barSeries = new HashMap<>(21);
        rates.keySet().forEach(timeframe -> {
            final Duration duration = timeframeDurationMap.get(timeframe);
            final BarSeries series = new BaseBarSeriesBuilder().build();
            final List<Rate> items = rates.get(timeframe);
            items.stream()
                    .map(item -> BaseBar
                            .builder(DoubleNum::valueOf, Double.class)
                            .volume(Double.valueOf(item.getTickVolume()))
                            .closePrice(item.getClose())
                            .openPrice(item.getOpen())
                            .highPrice(item.getHigh())
                            .lowPrice(item.getLow())
                            .endTime(item.getTime())
                            .timePeriod(duration)
                            .build()
                    ).forEachOrdered(series::addBar);
            barSeries.put(duration, series);
        });
        return barSeries;
    }

}
