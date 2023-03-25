package o.horbenko.ua.nlp.config;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import o.horbenko.ua.nlp.service.error.TextAnalysisException;
import o.horbenko.ua.nlp.utils.ResourceFileUtils;
import org.apache.lucene.analysis.hunspell.Dictionary;
import org.apache.lucene.store.ByteBuffersDirectory;
import org.apache.lucene.store.Directory;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;

@UtilityClass
@Slf4j
@Getter
public class UaHunspellDictionaryProvider {

    private static final String TEMPORARY_SYS_FILE_PREFIX = "hunspell_temp_file_pref";
    private static final String AFFIX_RESOURCE_FILE_RELATIVE_PATH = "lang.hunspell.ua/uk_UA.aff";
    private static final String DICT_RESOURCE_FILE_RELATIVE_PATH = "lang.hunspell.ua/uk_UA.dic";

    public Dictionary initDictionary() {
        var luceneDirectory = initByteBufferLuceneDirectory();
        return initDictionary(luceneDirectory);
    }

    private Dictionary initDictionary(Directory luceneDirectory) {
        var affix = ResourceFileUtils.asInputStream(AFFIX_RESOURCE_FILE_RELATIVE_PATH);
        var dict = ResourceFileUtils.asInputStream(DICT_RESOURCE_FILE_RELATIVE_PATH);
        return initDictionary(luceneDirectory, affix, dict);
    }


    private Dictionary initDictionary(Directory luceneDirectory,
                                             InputStream affixIs,
                                             InputStream dictIs) {
        try {

            return new Dictionary(
                    luceneDirectory,
                    TEMPORARY_SYS_FILE_PREFIX,
                    affixIs,
                    dictIs
            );
        } catch (IOException | ParseException e) {
            log.error("Unable to construct Ukrainian Hunspell Dictionary. ", e);
            throw new TextAnalysisException(e);
        }
    }

    private Directory initByteBufferLuceneDirectory() {
        return new ByteBuffersDirectory();
    }
}
