package com.tartarika.transliterator.service;

import com.tartarika.transliterator.exceptions.SourceTextIsNullException;
import com.tartarika.transliterator.utils.FileUtils;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * SpecificTatarLettersService.
 * Service for work with specific tatar letters.
 * @author Aydar_Safiullin
 */
@Component("Tatar")
public class TatarConvertingService implements LatinService{
    private Properties cyrillicAlphabet;
    private Properties latinAlphabet;
    private Properties specificLetters;
    private Properties conditions;

    public TatarConvertingService() {
        try(FileReader latinAlphabetReader = new FileReader(FileUtils.getLatinAlphabet());
            FileReader cyrillicAlphabetReader = new FileReader(FileUtils.getCyrillicAlphabet());
            FileReader specificLettersReader = new FileReader(FileUtils.getSpecificTatarLetters());
            FileReader conditionsReader = new FileReader(FileUtils.getConditionProperties())) {

            latinAlphabet = new Properties();
            cyrillicAlphabet = new Properties();
            specificLetters = new Properties();
            conditions = new Properties();

            latinAlphabet.load(latinAlphabetReader);
            cyrillicAlphabet.load(cyrillicAlphabetReader);
            specificLetters.load(specificLettersReader);
            conditions.load(conditionsReader);

        } catch (IOException e) {
            // TODO: 03.04.2021 handle this exception + may be add a PropertiesNotFoundException?
        }
    }

    @Override
    public String convertToLatin(String sourceText) {
        if (sourceText == null) {
            throw new SourceTextIsNullException();
        }

        StringJoiner joiner = new StringJoiner("");
        List<String> symbols = getSymbols(sourceText);

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

    @Override
    public String convertToCyrillic(String sourceText) {
        if (sourceText == null) {
            throw new SourceTextIsNullException();
        }

        return getSymbols(sourceText).stream()
                .map(originalSymbol -> {
                    String cyrillicSymbol = latinAlphabet.getProperty(originalSymbol);
                    if (null != cyrillicSymbol) {
                        return cyrillicSymbol;
                    }
                    return originalSymbol;
                }).collect(Collectors.joining());
    }

    /**
     * Convert cyrillic "В" letter to latin by rules of Tatar language.
     * @param currentSymbol - current symbol;
     * @param nextSymbol - next symbol;
     * @return converted symbol.
     */
    private String convertV(String previousSymbol, String currentSymbol, String nextSymbol) {
        String vowels = conditions.get("vowels").toString();
        if (null == nextSymbol && vowels.contains(previousSymbol)) {
                return specificLetters.getProperty(currentSymbol);

        }

        if (null !=nextSymbol && vowels.contains(nextSymbol)) {
            return specificLetters.getProperty(currentSymbol);
        }

        return cyrillicAlphabet.getProperty(currentSymbol);
    }

    private String convertGAndK(String previousSymbol, String currentSymbol, String nextSymbol) {
        return cyrillicAlphabet.getProperty(currentSymbol);
    }
}
