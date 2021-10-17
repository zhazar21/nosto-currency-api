package tr.currency.api.web.exception;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;

public class CurrencyException extends Throwable {

    private String errorCode;
    private IOException iOException;

    public CurrencyException(String errorCode, JsonProcessingException e) {
        this.errorCode = errorCode;
        this.iOException = e;
    }
}
