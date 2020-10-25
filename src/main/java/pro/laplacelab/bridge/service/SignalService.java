package pro.laplacelab.bridge.service;

import pro.laplacelab.bridge.model.Market;
import pro.laplacelab.bridge.model.Signal;

public interface SignalService {
    Signal analyze(Market request);
}
