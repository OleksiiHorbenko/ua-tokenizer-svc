package o.horbenko.ua.nlp.service.error;

public class TextAnalysisException extends RuntimeException {

    public TextAnalysisException(Exception e) {
        super(e);
    }
}
