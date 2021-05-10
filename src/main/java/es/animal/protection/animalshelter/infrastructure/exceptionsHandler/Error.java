package es.animal.protection.animalshelter.infrastructure.exceptionsHandler;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
class Error {

    private final String error;
    private final String message;
    private final Integer code;

    Error(Exception exception, Integer code) {
        this.error = exception.getClass().getSimpleName();
        this.message = exception.getMessage();
        this.code = code;
    }

}
