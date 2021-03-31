package hr.tvz.susac.java.web.flight.util.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@ControllerAdvice
public class RestExceptionHandlerUtil
{
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleBadRequestException(BadRequestException ex)
    {
        log.warn(ex.getMessage());

        if(!Objects.isNull(ex.getErrors()) && ex.getErrors().hasErrors())
            return new ResponseEntity<>(handleBadRequestValidationException(ex.getErrors()), HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<?> handleInternalServerException(InternalServerErrorException ex)
    {
        log.warn(ex.getMessage());

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedRequestException.class)
    public ResponseEntity<?> handleUnauthorizedRequestException(UnauthorizedRequestException ex)
    {
        log.warn(ex.getMessage());

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    public Map<String, String> handleBadRequestValidationException(Errors ex)
    {
        Map<String, String> errors = new HashMap<>();

        ex.getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();

            errors.put(fieldName, errorMessage);
        });

        log.warn(errors.toString());

        return errors;
    }
}
