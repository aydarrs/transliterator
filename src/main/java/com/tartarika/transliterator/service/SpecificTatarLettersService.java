package com.tartarika.transliterator.service;

import com.tartarika.transliterator.utils.FileUtils;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * SpecificTatarLettersService.
 * Service for work with specific tatar letters.
 * @author Aydar_Safiullin
 */
@Component
public class SpecificTatarLettersService {
    private Properties latinAlphabet;
    private Properties specificLetters;
    private Properties vowels;

    public SpecificTatarLettersService() {
        try(FileReader latinAlphabetReader = new FileReader(FileUtils.getLatinAlphabet());
            FileReader specificLettersReader = new FileReader(FileUtils.getSpecificTatarLetters());
            FileReader vowelsReader = new FileReader(FileUtils.getVowelsProperties())) {

            latinAlphabet = new Properties();
            specificLetters = new Properties();
            vowels = new Properties();

            latinAlphabet.load(latinAlphabetReader);
            specificLetters.        load(specificLettersReader);
            vowels.load(vowelsReader);

        } catch (IOException e) {
            // TODO: 03.04.2021 handle this exception + may be add a PropertiesNotFoundException?
        }
    }

    /**
     * Convert cyrillic "В" letter to latin by rules of Tatar language.
     * @param previousSymbol - previous symbol;
     * @param currentSymbol - current symbol;
     * @param nextSymbol - next symbol;
     * @return converted symbol.
     */
    protected String convertV(String previousSymbol, String currentSymbol, String nextSymbol) {
        if (null == previousSymbol || null == nextSymbol || vowels.containsKey(nextSymbol)) {
            return specificLetters.getProperty(currentSymbol);
        }

        return latinAlphabet.getProperty(currentSymbol);
    }
}
