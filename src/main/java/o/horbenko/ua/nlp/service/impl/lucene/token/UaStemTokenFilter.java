package o.horbenko.ua.nlp.service.impl.lucene.token;

import o.horbenko.ua.nlp.config.UaHunspellDictionaryProvider;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.hunspell.Dictionary;
import org.apache.lucene.analysis.hunspell.HunspellStemFilter;

import java.io.IOException;

public final class UaStemTokenFilter extends TokenFilter {

    private static final Dictionary uaDictionary =
            UaHunspellDictionaryProvider.initDictionary();

    private final HunspellStemFilter hunspellStemFilter;

    public UaStemTokenFilter(TokenStream ts) {
        this(ts, uaDictionary);
    }

    protected UaStemTokenFilter(TokenStream ts,
                                Dictionary dictionary) {
        super(ts);
        this.hunspellStemFilter = new HunspellStemFilter(input, dictionary);
    }

    @Override
    public boolean incrementToken() throws IOException {
        return this.hunspellStemFilter.incrementToken();
    }

    @Override
    public void end() throws IOException {
        this.hunspellStemFilter.end();
    }

    @Override
    public void reset() throws IOException {
        this.hunspellStemFilter.reset();
    }
}
