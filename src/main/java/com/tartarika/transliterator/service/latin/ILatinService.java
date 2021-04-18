package com.tartarika.transliterator.service.latin;

import com.tartarika.transliterator.lang.AppLanguage;

/**
 * LatinService.
 * Service for text writing converting.
 *
 * @author Aydar_Safiullin
 */
public interface ILatinService {
    /**
     * Convert source cyrillic text to latin.
     * @param sourceText - source text.
     * @return converting result.
     */
    String convertToLatin(String sourceText);

    /**
     * Convert source latin text to cyrillic.
     * @param sourceText - source text.
     * @return converting result.
     */
    String convertToCyrillic(String sourceText);

    /**
     * Gives the language used by the service.
     * @return service language.
     */
    AppLanguage getLanguage();
}
