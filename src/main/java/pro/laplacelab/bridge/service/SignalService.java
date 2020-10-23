package pro.laplacelab.bridge.service;

import pro.laplacelab.bridge.model.Signal;
import pro.laplacelab.bridge.model.SignalRequest;

public interface SignalService {
    Signal get(SignalRequest request);
}
