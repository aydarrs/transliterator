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
    private static Properties specificLetters;
    private static Properties vowels;

    static {
        try(FileReader latinAlphabetReader = new FileReader(FileUtils.getLatinAlphabet());
            FileReader cyrillicAlphabetReader = new FileReader(FileUtils.getCyrillicAlphabet());
            FileReader specificLettersReader = new FileReader(FileUtils.getSpecificTatarLetters());
            FileReader vowelsReader = new FileReader(FileUtils.getVowelsProperties())) {

            latinAlphabet = new Properties();
            cyrillicAlphabet = new Properties();
            specificLetters = new Properties();
            vowels = new Properties();

            latinAlphabet.load(latinAlphabetReader);
            cyrillicAlphabet.load(cyrillicAlphabetReader);
            specificLetters.load(specificLettersReader);
            vowels.load(vowelsReader);


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
    public static String convertTextToLatinWriting(String cyrillicText) {
        StringJoiner joiner = new StringJoiner("");
        List<String> symbols = getSymbols(cyrillicText);

        for (int i = 0; i < symbols.size(); i++) {
            String currentSymbol = symbols.get(i);
            String previousSymbol = null;
            String nextSymbol = null;

            if (currentSymbol.equalsIgnoreCase("в")) {
                if (i != 0) {
                    previousSymbol = symbols.get(i - 1);
                }
                if (i < symbols.size() - 1) {
                    nextSymbol = symbols.get(i + 1);
                }
                String latinV = convertV(previousSymbol, currentSymbol, nextSymbol);
                joiner.add(latinV);
                continue;
            }

            String latinSymbol = cyrillicAlphabet.getProperty(currentSymbol);
            joiner.add(Objects.requireNonNullElse(latinSymbol, currentSymbol));
        }

        return joiner.toString();
    }

    /**
     * Convert cyrillic "В" letter to latin by rules of Tatar language.
     * @param previousSymbol - previous symbol;
     * @param currentSymbol - current symbol;
     * @param nextSymbol - next symbol;
     * @return converted symbol.
     */
    private static String convertV(String previousSymbol, String currentSymbol, String nextSymbol) {
        if (null == previousSymbol || null == nextSymbol || vowels.containsKey(nextSymbol)) {
            return specificLetters.getProperty(currentSymbol);
        }

        return latinAlphabet.getProperty(currentSymbol);
    }

    /**
     * Convert latin text to cyrillic.
     * @param latinText - original text.
     * @return converted cyrillic text.
     */
    public static String convertTextToCyrillicLetters(String latinText) {
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
