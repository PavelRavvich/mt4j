package pro.laplacelab.bridge.service;

import pro.laplacelab.bridge.model.SignalRequest;
import pro.laplacelab.bridge.model.SignalResponse;

public interface SignalService {
    SignalResponse get(SignalRequest request);
}
