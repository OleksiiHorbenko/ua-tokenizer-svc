package o.horbenko.ua.nlp.service.impl.lucene.token;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import o.horbenko.ua.nlp.service.SpellingService;
import o.horbenko.ua.nlp.service.impl.lucene.SpellSuggestionEnricher;
import org.apache.lucene.analysis.TokenStream;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class UaSpellSuggestionEnricher implements SpellSuggestionEnricher {

    private final SpellingService uaSpellingService;

    @Override
    public TokenStream enrichWithSuggestionsForMisspelled(TokenStream originalTs) {

        var tokenStrings = TokenStreamUtils.toStrings(originalTs);

        var suggestionsForMisspelled = tokenStrings.stream()
                .filter(maybeMisspelled -> !uaSpellingService.isValid(maybeMisspelled))
                .map(uaSpellingService::suggestLike)
                .flatMap(List::stream)
                .toList();

        var result = new ArrayList<String>();
        result.addAll(tokenStrings);
        result.addAll(suggestionsForMisspelled);

        return new StringsTokenStream(result);
    }

}
