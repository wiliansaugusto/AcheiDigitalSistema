package digital.achei.api.exception;

import org.springframework.http.HttpStatus;

public class BasicException extends RuntimeException {
    private final HttpStatus status;

    public BasicException(String message) {
        super(message);
        this.status = HttpStatus.BAD_REQUEST; // Defina um status padrão ou faça com que o status seja opcional
    }

    public BasicException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
