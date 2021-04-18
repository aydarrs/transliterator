package com.tartarika.transliterator.service.latin;

import com.tartarika.transliterator.lang.AppLanguage;
import com.tartarika.transliterator.utils.FileUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * LatinService.
 * First implementation of latin service.
 * To simplify further implementation.
 *
 * @author Aydar_Safiullin
 */
public abstract class LatinService implements ILatinService {
    protected Properties cyrillicAlphabet;
    protected Properties latinAlphabet;
    protected Properties specificLetters;
    protected Properties rulesHelper;
    protected AppLanguage language;

    protected LatinService () {
        language = getLanguage();
        latinAlphabet = FileUtils.getLatinAlphabet(language);
        cyrillicAlphabet = FileUtils.getCyrillicAlphabet(language);
        specificLetters = FileUtils.getSpecificLetters(language);
        rulesHelper = FileUtils.getRulesHelperProperties(language);
    }

    /**
     * Convert text to symbols collection.
     * @param sourceText - original text.
     * @return collection of original symbols.
     */
    protected List<String> getSymbols(String sourceText) {
        List<String> result = new ArrayList<>();
        char[] symbols = sourceText.toCharArray();
        for (char symbol : symbols) {
            result.add(String.valueOf(symbol));
        }
        return result;
    }
}
