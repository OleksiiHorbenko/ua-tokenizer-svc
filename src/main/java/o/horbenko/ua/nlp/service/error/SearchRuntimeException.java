package o.horbenko.ua.nlp.service.error;

public class SearchRuntimeException extends RuntimeException {

    public SearchRuntimeException(Exception e) {
        super(e);
    }
}
