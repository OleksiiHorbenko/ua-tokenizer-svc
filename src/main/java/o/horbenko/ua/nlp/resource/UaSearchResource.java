package o.horbenko.ua.nlp.resource;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import o.horbenko.ua.nlp.dto.SearchRequestDto;
import o.horbenko.ua.nlp.service.error.SearchRuntimeException;
import o.horbenko.ua.nlp.service.impl.UaLuceneSearchSessionFactory;
import org.apache.lucene.search.Explanation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ua/search")
@Slf4j
@RequiredArgsConstructor
public class UaSearchResource {

    private final UaLuceneSearchSessionFactory searchSessionFactory;

    @PostMapping("/search")
    public Double searchKeywords(@RequestBody SearchRequestDto input) {
        try (var searchSession = searchSessionFactory.sessionFromText(input.getTextToSearchIn())) {

            return searchSession.match(input.getToSearch());

        } catch (Exception e) {
            log.error("Unable to process REST request to search words in text. ", e);
            throw new SearchRuntimeException(e);
        }
    }

    @PostMapping("/search-with-explanation")
    public Explanation searchKeywordsWithExplanation(@RequestBody SearchRequestDto input) {
        try (var searchSession = searchSessionFactory.sessionFromText(input.getTextToSearchIn())) {

            return searchSession.explain(input.getToSearch());


        } catch (Exception e) {
            log.error("Unable to process REST request to search words in text. ", e);
            throw new SearchRuntimeException(e);
        }
    }

    @PostMapping("/filter-matched-only")
    public List<String> filterMatchedOnly(@RequestBody SearchRequestDto input) {
        try (var searchSession = searchSessionFactory.sessionFromText(input.getTextToSearchIn())) {

            return input
                    .getToSearch()
                    .stream()
                    .filter(wordToSearch -> searchSession.match(wordToSearch) > 0)
                    .toList();

        } catch (Exception e) {
            log.error("Unable to process REST request to search words in text. ", e);
            throw new SearchRuntimeException(e);
        }
    }

}
