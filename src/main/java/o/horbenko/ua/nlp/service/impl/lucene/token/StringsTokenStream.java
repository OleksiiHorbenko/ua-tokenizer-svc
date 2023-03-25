package o.horbenko.ua.nlp.service.impl.lucene.token;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;

import java.util.Collection;
import java.util.Iterator;

public class StringsTokenStream extends TokenStream {

    private final CharTermAttribute charTermAttribute;
    private final OffsetAttribute offsetAttribute;
    private final Iterator<String> stringsIterator;

    private int start = 0;

    public StringsTokenStream(Collection<String> stringsIterator) {
        this.charTermAttribute = addAttribute(CharTermAttribute.class);
        this.offsetAttribute = addAttribute(OffsetAttribute.class);
        this.stringsIterator = stringsIterator.iterator();
    }

    @Override
    public boolean incrementToken() {
        if (!stringsIterator.hasNext())
            return false;

        var term = stringsIterator.next();
        clearAttributes();
        charTermAttribute.setEmpty().append(term);
        offsetAttribute.setOffset(start, start + charTermAttribute.length());
        start += term.length() + 1;
        return true;
    }
}
