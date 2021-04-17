package com.tartarika.transliterator.service;

import com.tartarika.transliterator.exceptions.SourceTextIsNullException;
import com.tartarika.transliterator.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * GeneralAlphabetService.
 * App main service for work with alphabets.
 *
 * @author Aydar_Safiullin
 */
@Component
public class GeneralAlphabetService {
    private Properties latinAlphabet;
    private Properties cyrillicAlphabet;
    private SpecificTatarLettersService specificLettersService;

    @Autowired
    public GeneralAlphabetService(SpecificTatarLettersService service) {
        try(FileReader latinAlphabetReader = new FileReader(FileUtils.getLatinAlphabet());
            FileReader cyrillicAlphabetReader = new FileReader(FileUtils.getCyrillicAlphabet())) {

            latinAlphabet = new Properties();
            cyrillicAlphabet = new Properties();

            latinAlphabet.load(latinAlphabetReader);
            cyrillicAlphabet.load(cyrillicAlphabetReader);

            this.specificLettersService = service;

        } catch (IOException e) {
            // TODO: 03.04.2021 handle this exception + may be add a PropertiesNotFoundException?
        }
    }

    /**
     * Convert cyrillic text to latin.
     * @param cyrillicText - original text.
     * @return converted latin text.
     */
    public String convertTextToLatinWriting(String cyrillicText) {
        if (cyrillicText == null) {
            throw new SourceTextIsNullException();
        }

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
                String latinV = specificLettersService.convertV(previousSymbol, currentSymbol, nextSymbol);
                joiner.add(latinV);
                continue;
            }

            String latinSymbol = cyrillicAlphabet.getProperty(currentSymbol);
            joiner.add(Objects.requireNonNullElse(latinSymbol, currentSymbol));
        }

        return joiner.toString();
    }

    /**
     * Convert latin text to cyrillic.
     * @param latinText - original text.
     * @return converted cyrillic text.
     */
    public String convertTextToCyrillicWriting(String latinText) {
        if (latinText == null) {
            throw new SourceTextIsNullException();
        }

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
    private List<String> getSymbols(String sourceText) {
        List<String> result = new ArrayList<>();
        char[] symbols = sourceText.toCharArray();
        for (char symbol : symbols) {
            result.add(String.valueOf(symbol));
        }
        return result;
    }

}
