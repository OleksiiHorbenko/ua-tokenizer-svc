package o.horbenko.ua.nlp.config;

import o.horbenko.ua.nlp.service.SpellingService;
import o.horbenko.ua.nlp.service.impl.UaSpellingService;
import org.apache.lucene.analysis.hunspell.Hunspell;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UaSpellingConfiguration {

    @Bean
    public SpellingService uaSpellingService() {
        var hunspellDictionary = UaHunspellDictionaryProvider.initDictionary();
        var hunspell = new Hunspell(hunspellDictionary);
        return new UaSpellingService(hunspell);
    }

}
