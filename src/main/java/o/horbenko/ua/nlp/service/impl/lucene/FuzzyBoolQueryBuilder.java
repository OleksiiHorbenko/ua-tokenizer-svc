package o.horbenko.ua.nlp.service.impl.lucene;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.Query;

import java.util.Collection;

public class FuzzyBoolQueryBuilder {

    public Query buildBoolShouldFuzzyQuery(String indexFieldName,
                                           Collection<String> tokensToSearch) {
        var queryBuilder = new BooleanQuery.Builder();

        // Build sub-queries (FuzzyQuery) and add them as Lucene Should clause to the compound bool query
        tokensToSearch.stream()
                .map(term -> buldFuzzyQuery(indexFieldName, term))
                .forEach(subQuery -> queryBuilder.add(subQuery, BooleanClause.Occur.SHOULD));

        return queryBuilder.build();
    }

    public FuzzyQuery buldFuzzyQuery(String indexFieldName,
                                     String termToMatch) {
        var term = new Term(indexFieldName, termToMatch);
        return new FuzzyQuery(term);
    }


}
