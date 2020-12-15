package pro.laplacelab.mt4j.adapter.ta4j;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import org.ta4j.core.Bar;
import org.ta4j.core.BaseBar;
import org.ta4j.core.num.Num;

import java.time.Duration;
import java.time.ZonedDateTime;

@AllArgsConstructor
@EqualsAndHashCode
public class TBar implements Bar {

    private final BaseBar baseBar;

    private final int spread;

    public static TBarBuilder builder() {
        return new TBarBuilder();
    }

    @Override
    public Num getOpenPrice() {
        return baseBar.getOpenPrice();
    }

    public int getSpread() {
        return spread;
    }

    @Override
    public Num getLowPrice() {
        return baseBar.getLowPrice();
    }

    @Override
    public Num getHighPrice() {
        return baseBar.getHighPrice();
    }

    @Override
    public Num getClosePrice() {
        return baseBar.getClosePrice();
    }

    @Override
    public Num getVolume() {
        return baseBar.getVolume();
    }

    @Override
    public int getTrades() {
        return baseBar.getTrades();
    }

    @Override
    public Num getAmount() {
        return baseBar.getAmount();
    }

    @Override
    public Duration getTimePeriod() {
        return baseBar.getTimePeriod();
    }

    @Override
    public ZonedDateTime getBeginTime() {
        return baseBar.getBeginTime();
    }

    @Override
    public ZonedDateTime getEndTime() {
        return baseBar.getEndTime();
    }

    @Override
    public void addTrade(final Num tradeVolume, final Num tradePrice) {
        throw new UnsupportedOperationException("addTrade() not implemented for the while");
    }

    @Override
    public void addPrice(final Num price) {
        throw new UnsupportedOperationException("addPrice() not implemented for the while");
    }
}
