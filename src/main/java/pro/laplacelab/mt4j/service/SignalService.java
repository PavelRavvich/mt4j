package pro.laplacelab.mt4j.service;

import pro.laplacelab.mt4j.model.Market;
import pro.laplacelab.mt4j.model.Signal;

public interface SignalService {
    Signal onTick(Market request);
}
