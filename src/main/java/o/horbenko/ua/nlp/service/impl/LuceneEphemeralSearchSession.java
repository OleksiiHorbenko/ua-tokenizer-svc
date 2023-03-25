package o.horbenko.ua.nlp.service.impl;

import o.horbenko.ua.nlp.service.EphemeralSearchSession;
import o.horbenko.ua.nlp.service.SpellBasedTokenizer;
import o.horbenko.ua.nlp.service.impl.lucene.FuzzyBoolQueryBuilder;
import o.horbenko.ua.nlp.service.impl.lucene.LuceneInMemorySearchSession;
import o.horbenko.ua.nlp.service.impl.lucene.token.StringsTokenStream;
import org.apache.lucene.search.Explanation;

import java.util.Collection;
import java.util.List;

public class LuceneEphemeralSearchSession implements EphemeralSearchSession, AutoCloseable {

    private static final String DEFAULT_TEXT_FIELD = "DEFAULT_TEXT_FIELD";

    private final SpellBasedTokenizer tokenizer;
    private final FuzzyBoolQueryBuilder queryBuilder;
    private final LuceneInMemorySearchSession searchSession;

    public LuceneEphemeralSearchSession(SpellBasedTokenizer tokenizer,
                                        FuzzyBoolQueryBuilder queryBuilder,
                                        String documentText) {
        this.tokenizer = tokenizer;
        this.queryBuilder = queryBuilder;
        var documentTokens = tokenizer.tokenizeWithSpellSuggestions(documentText);
        var documentTokenStream = new StringsTokenStream(documentTokens);
        this.searchSession = new LuceneInMemorySearchSession(documentTokenStream);
    }

    @Override
    public Double match(String toMatch) {
        var termsToMatch = tokenizer.tokenizeWithSpellSuggestions(toMatch);
        var luceneQuery = queryBuilder.buildBoolShouldFuzzyQuery(DEFAULT_TEXT_FIELD, termsToMatch);
        return searchSession.search(luceneQuery);
    }

    @Override
    public Double match(Collection<String> toMatch) {
        var termsToMatch = toMatch.stream()
                .map(tokenizer::tokenizeWithSpellSuggestions)
                .flatMap(List::stream)
                .toList();
        var luceneQuery = queryBuilder.buildBoolShouldFuzzyQuery(DEFAULT_TEXT_FIELD, termsToMatch);
        return searchSession.search(luceneQuery);
    }

    public Explanation explain(Collection<String> toMatch) {
        var luceneQuery = queryBuilder.buildBoolShouldFuzzyQuery(DEFAULT_TEXT_FIELD, toMatch);
        return searchSession.explainSearchResults(luceneQuery);
    }

    @Override
    public void close() throws Exception {
        this.searchSession.close();
    }
}
