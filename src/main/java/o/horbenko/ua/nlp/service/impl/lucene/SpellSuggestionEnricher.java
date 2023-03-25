package o.horbenko.ua.nlp.service.impl.lucene;

import org.apache.lucene.analysis.TokenStream;

public interface SpellSuggestionEnricher {
    TokenStream enrichWithSuggestionsForMisspelled(TokenStream original);
}
