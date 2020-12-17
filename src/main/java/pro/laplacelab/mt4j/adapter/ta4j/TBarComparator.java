package pro.laplacelab.mt4j.adapter.ta4j;

import java.util.Comparator;

public class TBarComparator implements Comparator<TBar> {
    @Override
    public int compare(final TBar bar1, final TBar bar2) {
        return bar1.getOpenPrice().compareTo(bar2.getOpenPrice());
    }
}
