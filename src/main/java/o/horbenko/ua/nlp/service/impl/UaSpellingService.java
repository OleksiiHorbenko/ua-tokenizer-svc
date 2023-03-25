package o.horbenko.ua.nlp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import o.horbenko.ua.nlp.service.SpellingService;
import org.apache.lucene.analysis.hunspell.Hunspell;

import java.util.List;

/**
 * Spring Bean initialized via {@link  o.horbenko.ua.nlp.config.UaSpellingConfiguration}
 */
@Slf4j
@RequiredArgsConstructor
public class UaSpellingService implements SpellingService {

    private final Hunspell hunspell;

    @Override
    public boolean isValid(String word) {
        log.debug("Trying to check is the word '{}' valid or misspelled. ", word);
        return hunspell.spell(word);
    }

    @Override
    public List<String> suggestLike(String word) {
        log.debug("Trying to find phonetically similar word to '{}' ", word);
        return hunspell.suggest(word);
    }

    @Override
    public List<String> getRootForms(String of) {
        log.debug("Trying to identify root forms off '{}'", of);
        return hunspell.getRoots(of);
    }

}
