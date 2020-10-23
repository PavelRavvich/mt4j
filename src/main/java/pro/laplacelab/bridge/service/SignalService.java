package pro.laplacelab.bridge.service;

import pro.laplacelab.bridge.model.Sequence;
import pro.laplacelab.bridge.model.Signal;

import java.util.List;

public interface SignalService {
    Signal get(List<Sequence> sequences);
}
