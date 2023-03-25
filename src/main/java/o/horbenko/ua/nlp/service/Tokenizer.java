package o.horbenko.ua.nlp.service;

import java.util.List;

public interface Tokenizer {

    List<String> tokenize(String text);

}
