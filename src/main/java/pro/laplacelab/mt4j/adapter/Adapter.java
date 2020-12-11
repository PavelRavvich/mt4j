package pro.laplacelab.mt4j.adapter;

import pro.laplacelab.mt4j.enums.Timeframe;
import pro.laplacelab.mt4j.model.Rate;

import java.util.List;
import java.util.Map;

/**
 * Adapter map stock data to technical indicator library specific format.
 *
 * @param <T> - Class of Timeframe, analog from target technical indicator library.
 * @param <R> - Class of Rate array, analog from target technical indicator library.
 */
public interface Adapter<T, R> {

    Map<T, R> map(Map<Timeframe, List<Rate>> rates);

}
