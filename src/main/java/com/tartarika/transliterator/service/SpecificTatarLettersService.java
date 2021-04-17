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
    private Properties cyrillicAlphabet;
    private Properties specificLetters;
    private Properties conditions;

    public SpecificTatarLettersService() {
        try(FileReader cyrillicAlphabetReader = new FileReader(FileUtils.getCyrillicAlphabet());
            FileReader specificLettersReader = new FileReader(FileUtils.getSpecificTatarLetters());
            FileReader conditionsReader = new FileReader(FileUtils.getConditionProperties())) {

            cyrillicAlphabet = new Properties();
            specificLetters = new Properties();
            conditions = new Properties();

            cyrillicAlphabet.load(cyrillicAlphabetReader);
            specificLetters.load(specificLettersReader);
            conditions.load(conditionsReader);

        } catch (IOException e) {
            // TODO: 03.04.2021 handle this exception + may be add a PropertiesNotFoundException?
        }
    }

    /**
     * Convert cyrillic "В" letter to latin by rules of Tatar language.
     * @param currentSymbol - current symbol;
     * @param nextSymbol - next symbol;
     * @return converted symbol.
     */
    protected String convertV(String previousSymbol, String currentSymbol, String nextSymbol) {
        String vowels = conditions.get("vowels").toString();
        if (null == nextSymbol && vowels.contains(previousSymbol)) {
                return specificLetters.getProperty(currentSymbol);

        }

        if (null !=nextSymbol && vowels.contains(nextSymbol)) {
            return specificLetters.getProperty(currentSymbol);
        }

        return cyrillicAlphabet.getProperty(currentSymbol);
    }

    protected String convertGAndK(String previousSymbol, String currentSymbol, String nextSymbol) {
        return cyrillicAlphabet.getProperty(currentSymbol);
    }
}
