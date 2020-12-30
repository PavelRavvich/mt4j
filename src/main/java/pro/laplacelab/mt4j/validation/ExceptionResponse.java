package pro.laplacelab.mt4j.validation;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse {

    private String message;

    private Boolean isCritical;

    @JsonFormat(shape = STRING, pattern = "dd.MM.yyyy HH:mm")
    private final LocalDateTime time = LocalDateTime.now();

}
