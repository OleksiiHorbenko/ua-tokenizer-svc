package o.horbenko.ua.nlp.resource;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import o.horbenko.ua.nlp.dto.TextDto;
import o.horbenko.ua.nlp.service.impl.UaTokenizerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ua/tokenization")
@Slf4j
@RequiredArgsConstructor
public class UaTokenizationResource {

    private final UaTokenizerService uaTokenizer;

    @PostMapping("/tokenize")
    public List<String> tokenize(@RequestBody TextDto input) {
        return uaTokenizer.tokenize(input.getText());
    }

    @PostMapping("/tokenize-with-suggestions-for-misspelled")
    public List<String> tokenizeWithSpellSuggestions(@RequestBody TextDto input) {
        return uaTokenizer.tokenizeWithSpellSuggestions(input.getText());
    }
}
