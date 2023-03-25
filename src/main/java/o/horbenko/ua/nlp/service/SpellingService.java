package o.horbenko.ua.nlp.service;

import java.util.List;

public interface SpellingService {

    boolean isValid(String word);

    List<String> suggestLike(String word);

    List<String> getRootForms(String of);

}
