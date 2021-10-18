package tr.currency.api.web.exception;

public class CurrencyException extends Exception {

    private final String errorCode;
    private final String detail;

    public CurrencyException(String errorCode, String detail) {
        this.errorCode = errorCode;
        this.detail = detail;
    }
}
