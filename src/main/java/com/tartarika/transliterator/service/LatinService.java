package com.tartarika.transliterator.service;

import java.util.ArrayList;
import java.util.List;

/**
 * LatinService.
 *
 * @author Aydar_Safiullin
 */
public interface LatinService {
    String convertToLatin(String sourceText);

    String convertToCyrillic(String sourceText);

    /**
     * Convert text to symbols collection.
     * @param sourceText - original text.
     * @return collection of original symbols.
     */
   default List<String> getSymbols(String sourceText) {
        List<String> result = new ArrayList<>();
        char[] symbols = sourceText.toCharArray();
        for (char symbol : symbols) {
            result.add(String.valueOf(symbol));
        }
        return result;
    }
}
