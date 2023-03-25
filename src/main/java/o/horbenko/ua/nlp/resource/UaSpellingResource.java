package o.horbenko.ua.nlp.resource;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import o.horbenko.ua.nlp.dto.WordDto;
import o.horbenko.ua.nlp.service.SpellingService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ua/spelling")
@Slf4j
@RequiredArgsConstructor
public class UaSpellingResource {

    private final SpellingService uaSpellingService;

    @PostMapping("/suggest")
    public List<String> findSimilarTo(@RequestBody WordDto input) {
        return uaSpellingService.suggestLike(input.getWord());
    }

    @PostMapping("/is-spelled-correctly")
    public Boolean isValid(@RequestBody WordDto toCheck) {
        return uaSpellingService.isValid(toCheck.getWord());
    }

    @PostMapping("/get-root-forms")
    public List<String> getRootForms(@RequestBody WordDto toGetRootsFrom) {
        return uaSpellingService.getRootForms(toGetRootsFrom.getWord());
    }

}
