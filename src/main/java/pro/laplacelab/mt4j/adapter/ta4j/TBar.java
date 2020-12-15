package pro.laplacelab.mt4j.adapter.ta4j;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.ta4j.core.Bar;
import org.ta4j.core.BaseBar;
import org.ta4j.core.num.Num;

import java.time.Duration;
import java.time.ZonedDateTime;

@Data
@Builder
@EqualsAndHashCode
public class TBar implements Bar {

    BaseBar baseBar;

    private Integer spread;

    @Override
    public Num getOpenPrice() {
        return baseBar.getOpenPrice();
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
    public void addTrade(Num tradeVolume, Num tradePrice) {
        baseBar.addTrade(tradeVolume, tradePrice);
    }

    @Override
    public void addPrice(Num price) {
        baseBar.addPrice(price);
    }
}
