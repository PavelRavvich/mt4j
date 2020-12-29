package pro.laplacelab.mt4j.validation;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

@Data
@Builder
public class ExceptionResponse {

    private final String message;

    private final Boolean isCritical;

    @JsonFormat(shape = STRING, pattern = "dd.MM.yyyy hh:mm")
    private final LocalDateTime time = LocalDateTime.now();

}
