package ir.alimalek.assignment.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionControllerAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler({HttpException.class})
    public ResponseEntity<ExceptionDTO> handleKnownErrors(HttpException httpException) {
        return ResponseEntity.status(httpException.getStatusCode()).body(new ExceptionDTO(httpException.getErrorMessage()));
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ExceptionDTO> handleUnknownErrors(Exception e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionDTO("Internal Server Error."));
    }
}
