package o.horbenko.ua.nlp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import o.horbenko.ua.nlp.service.SpellBasedTokenizer;
import o.horbenko.ua.nlp.service.Tokenizer;
import o.horbenko.ua.nlp.service.impl.lucene.SpellSuggestionEnricher;
import o.horbenko.ua.nlp.service.impl.lucene.token.TokenStreamUtils;
import o.horbenko.ua.nlp.service.impl.lucene.token.UaStemTokenFilter;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.uk.UkrainianMorfologikAnalyzer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UaTokenizerService implements Tokenizer, SpellBasedTokenizer {

    private static final Analyzer UKRAINIAN_MORFOLOGIK_ANALYZER = new UkrainianMorfologikAnalyzer();

    private final SpellSuggestionEnricher spellSuggestionEnricher;
    private final Analyzer analyzer;

    @Autowired
    public UaTokenizerService(SpellSuggestionEnricher spellSuggestionEnricher) {
        this(spellSuggestionEnricher, UKRAINIAN_MORFOLOGIK_ANALYZER);
    }

    @Override
    public List<String> tokenizeWithSpellSuggestions(String text) {
        var initialTokenStream = analyzer.tokenStream(null, text);
        var enriched = spellSuggestionEnricher.enrichWithSuggestionsForMisspelled(initialTokenStream);
        var stemFilteredTs = new UaStemTokenFilter(enriched);
        return TokenStreamUtils.toStrings(stemFilteredTs);
    }

    @Override
    public List<String> tokenize(String text) {
        var initialTokenStream = analyzer.tokenStream(null, text);
        var stemFilteredTs = new UaStemTokenFilter(initialTokenStream);
        return TokenStreamUtils.toStrings(stemFilteredTs);
    }
}
