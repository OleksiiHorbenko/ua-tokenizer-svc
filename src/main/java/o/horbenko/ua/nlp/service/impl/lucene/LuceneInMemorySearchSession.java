package o.horbenko.ua.nlp.service.impl.lucene;

import lombok.extern.slf4j.Slf4j;
import o.horbenko.ua.nlp.service.error.SearchRuntimeException;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.index.memory.MemoryIndex;
import org.apache.lucene.search.Explanation;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
public class LuceneInMemorySearchSession implements AutoCloseable {

    private static final String DEFAULT_TEXT_FIELD = "DEFAULT_TEXT_FIELD";
    private static final Integer MAX_DOCUMENTS_COUNT_IN_INDEX = 1;

    private final MemoryIndex memoryIndex;
    private final IndexSearcher indexSearcher;

    public LuceneInMemorySearchSession(TokenStream tokenStream) {
        this(createSingleFieldIndex(tokenStream));
    }

    public LuceneInMemorySearchSession(MemoryIndex memoryIndex) {
        this.memoryIndex = memoryIndex;
        this.indexSearcher = memoryIndex.createSearcher();
    }

    /**
     * @return final Search Score of the query execution against in-memory Index Document
     */
    public Double search(Query toExecute) {
        try {

            var topDocs = indexSearcher.search(toExecute, MAX_DOCUMENTS_COUNT_IN_INDEX);
            return Arrays.stream(topDocs.scoreDocs)
                    .collect(Collectors.summarizingDouble(doc -> doc.score))
                    .getSum();

        } catch (IOException e) {
            log.error("Unable to execute search query. ", e);
            throw new SearchRuntimeException(e);
        }
    }

    public Explanation explainSearchResults(Query toExecute) {
        try {

            return indexSearcher.explain(toExecute, 0);

        } catch (IOException e) {
            log.error("Unable to explain search scoring.", e);
            throw new SearchRuntimeException(e);
        }
    }

    @Override
    public void close() throws Exception {
        this.memoryIndex.reset();
    }

    private static MemoryIndex createSingleFieldIndex(TokenStream tokenStream) {
        var index = new MemoryIndex();
        index.addField(DEFAULT_TEXT_FIELD, tokenStream);
        return index;
    }
}
