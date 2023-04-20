package pl.mirekgab.springtransactions.errorhandler;

public class AppRuntimeException extends RuntimeException {

    public AppRuntimeException(String message) {
        super(message);
    }
}
