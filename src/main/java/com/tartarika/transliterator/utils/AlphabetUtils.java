package com.tartarika.transliterator.utils;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * AlphabetUtils.
 * App main utils for work with alphabets.
 *
 * @author Aydar_Safiullin
 */
public class AlphabetUtils {
    private static Properties latinAlphabet;
    private static Properties cyrillicAlphabet;

    static {
        try(FileReader latinAlphabetReader = new FileReader(FileUtils.getLatinAlphabet());
            FileReader cyrillicAlphabetReader = new FileReader(FileUtils.getCyrillicAlphabet())) {

            latinAlphabet = new Properties();
            cyrillicAlphabet = new Properties();

            latinAlphabet.load(latinAlphabetReader);
            cyrillicAlphabet.load(cyrillicAlphabetReader);

        } catch (IOException e) {
            // TODO: 03.04.2021 handle this exception + may be add a PropertiesNotFoundException?
        }
    }

    private AlphabetUtils() {}

    /**
     * Convert cyrillic text to latin.
     * @param cyrillicText - original text.
     * @return converted latin text.
     */
    public static String convertToLatinLetters(String cyrillicText) {
        return getSymbols(cyrillicText).stream()
                .map(originalSymbol -> {
                    String latinSymbol = cyrillicAlphabet.getProperty(originalSymbol);
                    if (null != latinSymbol) {
                        return latinSymbol;
                    }
                    return originalSymbol;
                }).collect(Collectors.joining());
    }

    /**
     * Convert latin text to cyrillic.
     * @param latinText - original text.
     * @return converted cyrillic text.
     */
    public static String convertToCyrillicLetters(String latinText) {
        return getSymbols(latinText).stream()
                .map(originalSymbol -> {
                    String cyrillicSymbol = latinAlphabet.getProperty(originalSymbol);
                    if (null != cyrillicSymbol) {
                        return cyrillicSymbol;
                    }
                    return originalSymbol;
                }).collect(Collectors.joining());
    }

    /**
     * Convert text to symbols collection.
     * @param sourceText - original text.
     * @return collection of original symbols.
     */
    private static List<String> getSymbols(String sourceText) {
        List<String> result = new ArrayList<>();
        char[] symbols = sourceText.toCharArray();
        for (char symbol : symbols) {
            result.add(String.valueOf(symbol));
        }
        return result;
    }

}
