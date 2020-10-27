package pro.laplacelab.bridge.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pro.laplacelab.bridge.exception.AdvisorNotFoundException;
import pro.laplacelab.bridge.model.Advisor;
import pro.laplacelab.bridge.model.Position;

import javax.validation.constraints.NotNull;

@Service
@AllArgsConstructor
public class PositionServiceImpl implements PositionService {

    private final AdvisorService advisorService;

    @Override
    public Position add(final @NotNull Position position) {
        final Advisor advisor = advisorService.get(position.getAdvisorId())
                .orElseThrow(AdvisorNotFoundException::new);
        advisor.addPosition(position);
        return position;
    }

    @Override
    public Position update(final @NotNull Position position) {
        final Advisor advisor = advisorService.get(position.getAdvisorId())
                .orElseThrow(AdvisorNotFoundException::new);
        advisor.updatePosition(position);
        return position;
    }

    @Override
    public Position history(final @NotNull Position position) {
        final Advisor advisor = advisorService.get(position.getAdvisorId())
                .orElseThrow(AdvisorNotFoundException::new);
        advisor.toHistory(position);
        return position;
    }

}
