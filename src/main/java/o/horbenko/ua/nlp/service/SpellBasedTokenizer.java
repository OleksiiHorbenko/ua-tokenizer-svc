package o.horbenko.ua.nlp.service;

import java.util.List;

public interface SpellBasedTokenizer {
    List<String> tokenizeWithSpellSuggestions(String text);
}
