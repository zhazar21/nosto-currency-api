package tr.currency.api.web.exception;

public class CurrencyNotfoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public CurrencyNotfoundException(String message) {
        super(message);
    }
}
