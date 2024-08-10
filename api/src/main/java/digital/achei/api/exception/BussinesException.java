package digital.achei.api.exception;

import com.fasterxml.jackson.core.JsonParseException;
import jakarta.validation.ConstraintViolationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class BussinesException extends Throwable {

    private static final Logger log = LogManager.getLogger(BussinesException.class);

    @ExceptionHandler(JsonParseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity jsonParseException(JsonParseException e) {
        Map<String, String> errors = new HashMap<>();

        Map<String, String> error = new HashMap<>();
        error.put("error", "Json com formato invalido: " + e.getOriginalMessage());

        log.error("JsonParseException Lançada: {}", error.get("error"));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity methodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();

        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        log.error("MethodArgumentNotValidException Lançada: {}", e.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);

    }


    @ExceptionHandler(BasicException.class)
    public ResponseEntity basicException(BasicException e) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", e.getMessage());
        log.error("BasicException Lançada: {}", e.getMessage());
        return new ResponseEntity<>(errors, e.getStatus());

    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity basicException(IllegalArgumentException e) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", e.getMessage());
        log.error("IllegalArgumentException Lançada: {}", e.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity ConstraintViolationException(ConstraintViolationException e) {
        Map<String, String> errors = new HashMap<>();

        String mensagem = String.format("O campo: %s regra: %s", e.getConstraintViolations().stream().findFirst().get().getPropertyPath(),
                e.getConstraintViolations().stream().findFirst().get().getMessage());
        errors.put("error", mensagem);
        log.error("IllegalArgumentException Lançada: {}", e.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity sQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", e.getMessage());
        log.error("SQLIntegrityConstraintViolationException Lançada: {}", e.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity httpMessageNotReadableException(HttpMessageNotReadableException e) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", e.getMessage());
        log.error("httpMessageNotReadableException Lançada: {}", e.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity nullPointerException(NullPointerException e) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", e.getMessage());
        log.error("NullPointerException Lançada: {}", e.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", e.getMessage());
        log.error("HttpRequestMethodNotSupportedException Lançada: {}", e.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.NOT_ACCEPTABLE);

    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity runtimeException(RuntimeException e) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", e.getMessage());
        log.error("RuntimeException Lançada: {}", e.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.NOT_ACCEPTABLE);

    }
}
