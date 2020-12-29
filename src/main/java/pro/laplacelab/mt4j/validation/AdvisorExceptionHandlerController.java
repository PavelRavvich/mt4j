package pro.laplacelab.mt4j.validation;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pro.laplacelab.mt4j.exception.DuplicateAdvisorException;

@ControllerAdvice
public class AdvisorExceptionHandlerController {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({DuplicateAdvisorException.class})
    public ExceptionResponse onDuplicateAdvisorException(final DuplicateAdvisorException ex) {
        return ExceptionResponse.builder()
                .message(ex.getMessage())
                .isCritical(true)
                .build();
    }

}
