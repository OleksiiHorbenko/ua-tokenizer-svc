package o.horbenko.ua.nlp.service.impl;

import lombok.extern.slf4j.Slf4j;
import o.horbenko.ua.nlp.service.SpellBasedTokenizer;
import o.horbenko.ua.nlp.service.impl.lucene.FuzzyBoolQueryBuilder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UaLuceneSearchSessionFactory {

    private final SpellBasedTokenizer tokenizer;
    private final FuzzyBoolQueryBuilder queryBuilder;

    public UaLuceneSearchSessionFactory(SpellBasedTokenizer tokenizer) {
        this.queryBuilder = new FuzzyBoolQueryBuilder();
        this.tokenizer = tokenizer;
    }

    public LuceneEphemeralSearchSession sessionFromText(String text) {
        return new LuceneEphemeralSearchSession(
                tokenizer, queryBuilder, text);
    }

}
