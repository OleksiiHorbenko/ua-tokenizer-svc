package o.horbenko.ua.nlp.service.impl.lucene.token;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import o.horbenko.ua.nlp.service.error.TextAnalysisException;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@UtilityClass
@Slf4j
public class TokenStreamUtils {

    public List<String> toStrings(TokenStream ts) {
        try (ts) {

            var result = new LinkedList<String>();
            var termAttribute = ts.addAttribute(CharTermAttribute.class);
            ts.reset();


            while (ts.incrementToken()) {
                var tokenAsString = termAttribute.toString();
                result.add(tokenAsString);
            }

            ts.end();
            return result;

        } catch (IOException e) {
            log.error("Unable to map TokenStream to strings list. ", e);
            throw new TextAnalysisException(e);
        }
    }

}
