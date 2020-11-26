package pro.laplacelab.mt4j.service;

import pro.laplacelab.mt4j.model.Market;
import pro.laplacelab.mt4j.model.Signal;

import java.util.List;

public interface SignalService {
    List<Signal> onTick(Market request);
}
