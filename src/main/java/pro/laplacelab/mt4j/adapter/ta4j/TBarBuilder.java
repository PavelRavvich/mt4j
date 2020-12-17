package pro.laplacelab.mt4j.adapter.ta4j;

import org.ta4j.core.BaseBar;
import org.ta4j.core.num.DoubleNum;
import org.ta4j.core.num.Num;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.function.Function;

public class TBarBuilder {

    private Duration timePeriod;
    private ZonedDateTime endTime;
    private Num openPrice;
    private Num closePrice;
    private Num highPrice;
    private Num lowPrice;
    private Num volume;
    private int spread;

    private final Function<Double, Num> conversionFunction = DoubleNum::valueOf;

    public TBarBuilder timePeriod(final Duration timePeriod) {
        this.timePeriod = timePeriod;
        return this;
    }

    public TBarBuilder endTime(final ZonedDateTime endTime) {
        this.endTime = endTime;
        return this;
    }

    public TBarBuilder openPrice(final Double openPrice) {
        this.openPrice = conversionFunction.apply(openPrice);
        return this;
    }

    public TBarBuilder closePrice(final Double closePrice) {
        this.closePrice = conversionFunction.apply(closePrice);
        return this;
    }

    public TBarBuilder highPrice(final Double highPrice) {
        this.highPrice = conversionFunction.apply(highPrice);
        return this;
    }

    public TBarBuilder lowPrice(final Double lowPrice) {
        this.lowPrice = conversionFunction.apply(lowPrice);
        return this;
    }

    public TBarBuilder volume(final Double volume) {
        this.volume = conversionFunction.apply(volume);
        return this;
    }

    public TBarBuilder spread(final int spread) {
        this.spread = spread;
        return this;
    }

    public TBar build() {
        final BaseBar baseBar = BaseBar
                .builder()
                .timePeriod(timePeriod)
                .endTime(endTime)
                .openPrice(openPrice)
                .highPrice(highPrice)
                .lowPrice(lowPrice)
                .closePrice(closePrice)
                .volume(volume)
                .build();
        return new TBar(baseBar, spread, new TBarComparator());
    }
}
