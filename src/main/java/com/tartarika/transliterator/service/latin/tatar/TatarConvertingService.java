package com.tartarika.transliterator.service.latin.tatar;

import com.tartarika.transliterator.exceptions.SourceTextIsNullException;
import com.tartarika.transliterator.lang.AppLanguage;
import com.tartarika.transliterator.service.latin.ILatinService;
import com.tartarika.transliterator.service.latin.LatinService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import static com.tartarika.transliterator.lang.AppLanguage.TATAR;

/**
 * TatarConvertingService.
 * {@link ILatinService} implementation for tatar language.
 *
 * @author Aydar_Safiullin
 */
@Component("Tatar")
public class TatarConvertingService extends LatinService {
    private static final String VOWELS = "vowels";
    private static final String HARD_VOWELS = "hardVowels";
    private static final String SOFT_SOUNDS = "softSounds";

    /**
     * {@inheritDoc}
     */
    @Override
    public String convertToLatin(String sourceText) {
        if (sourceText == null) {
            throw new SourceTextIsNullException();
        }

        StringJoiner joiner = new StringJoiner("");
        List<String> symbols = getSymbols(sourceText);

        for (int i = 0; i < symbols.size(); i++) {
            String latinSymbol;
            if (specificLetters.containsKey(symbols.get(i))) {
                latinSymbol = convertSpecificCyrillicTatarLetter(i, symbols);

            } else {
                latinSymbol = cyrillicAlphabet.getProperty(symbols.get(i));

            }

            joiner.add(Objects.requireNonNullElse(latinSymbol, symbols.get(i)));
        }

        return joiner.toString();
    }

    /**
     * {@inheritDoc}
     */
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
     * {@inheritDoc}
     */
    @Override
    public AppLanguage getLanguage() {
        return TATAR;
    }

    /**
     * Convert specific tatar letters from cyrillic alphabet to latin.
     *
     * @param index - current symbol index.
     * @param symbols - list of text symbols.
     * @return - converted symbol.
     */
    private String convertSpecificCyrillicTatarLetter(int index, List<String> symbols) {
        String currentSymbol = symbols.get(index);
        String latinSymbol = null;
        switch (currentSymbol.toUpperCase()) {
            case "В":
                latinSymbol = convertV(currentSymbol, getNextSymbol(index, symbols));
                break;
            case "Г":
            case "К":
                latinSymbol = convertGOrK(getPreviousSymbol(index, symbols), currentSymbol, getNextSymbol(index, symbols));
                break;
            case "Е":
                latinSymbol = convertE(getPreviousSymbol(index, symbols), currentSymbol);
                break;
            case "Ю":
            case "Я":
                latinSymbol = convertYaOrYu(getPreviousSymbol(index, symbols),
                        currentSymbol,
                        getNextSymbol(index, symbols),
                        isAmongNextSymbolsContainsSoftSounds(index, symbols));
                break;
            default:
        }
        return latinSymbol;
    }

    /**
     * Method for get next symbol in the text.
     *
     * @param index - current symbol index.
     * @param symbols - list of text symbols.
     * @return next symbol in list.
     */
    private String getNextSymbol(int index, List<String> symbols) {
        String nextSymbol = null;
        if (index < symbols.size() - 1) {
            nextSymbol = symbols.get(index + 1);
        }
        return nextSymbol;
    }

    /**
     * Method for get previous symbol in the text.
     *
     * @param index - current symbol index.
     * @param symbols - list of text symbols.
     * @return previous symbol in list.
     */
    private String getPreviousSymbol(int index, List<String> symbols) {
        String previousSymbol = null;
        if (index != 0) {
            previousSymbol = symbols.get(index - 1);
        }
        return previousSymbol;
    }

    /**
     * Check is among next symbols contains soft sounds.
     * @param index - current symbol index.
     * @param symbols - list of the text symbols.
     * @return {@code true} if soft sounds exist, else return {@code false}
     */
    private boolean isAmongNextSymbolsContainsSoftSounds(int index, List<String> symbols) {
        String softSounds = rulesHelper.getProperty(SOFT_SOUNDS);
        if (index == symbols.size()) {
            return false;
        }

        for (int i = index + 1; i < symbols.size(); i++) {
            String currentSymbol = symbols.get(i);
            if (softSounds.contains(currentSymbol)) {
                return true;
            }

            if (!cyrillicAlphabet.containsKey(currentSymbol)) {
                break;
            }
        }
        return false;
    }

    /**
     * Convert cyrillic "В" letter to latin by rules of Tatar language.
     *
     * @param currentSymbol - current symbol;
     * @param nextSymbol - next symbol;
     * @return converted symbol.
     */
    private String convertV(String currentSymbol, String nextSymbol) {
        String vowels = rulesHelper.get(VOWELS).toString();

        if (null !=nextSymbol && vowels.contains(nextSymbol)) {
            return specificLetters.getProperty(currentSymbol);
        }

        return cyrillicAlphabet.getProperty(currentSymbol);
    }

    /**
     * Convert cyrillic "Е" letter to latin by rules of Tatar language.
     *
     * @param previousSymbol - previous symbol.
     * @param currentSymbol - current symbol.
     * @return converted symbol.
     */
    private String convertE(String previousSymbol, String currentSymbol) {
        String vowels = rulesHelper.get(VOWELS).toString();

        if (null != previousSymbol && !vowels.contains(previousSymbol)) {
            return specificLetters.getProperty(currentSymbol);
        }

        return cyrillicAlphabet.getProperty(currentSymbol);
    }

    /**
     * Convert cyrillic "Г" and "К" letter to latin by rules of Tatar language.
     *
     * @param previousSymbol - previous symbol.
     * @param currentSymbol - current symbol.
     * @param nextSymbol - next symbol.
     * @return converted symbol.
     */
    private String convertGOrK(String previousSymbol, String currentSymbol, String nextSymbol) {
        String vowels = rulesHelper.get(VOWELS).toString();
        String hardVowels = rulesHelper.get(HARD_VOWELS).toString();

        if (null != nextSymbol && hardVowels.contains(nextSymbol)) {
            return specificLetters.getProperty(currentSymbol);
        }

        if (null != nextSymbol && !vowels.contains(nextSymbol) && null == previousSymbol) {
            return specificLetters.getProperty(currentSymbol);
        }

        if (null != nextSymbol && !vowels.contains(nextSymbol) && null != previousSymbol && hardVowels.contains(previousSymbol)) {
            return specificLetters.getProperty(currentSymbol);
        }


        return cyrillicAlphabet.getProperty(currentSymbol);
    }

    /**
     * Convert cyrillic "Я" and "Ю" letter to latin by rules of Tatar language.
     *
     * @return converted symbol.
     */
    private String convertYaOrYu(String previousSymbol, String currentSymbol, String nextSymbols, boolean hasSoftSymbols) {
        String softSounds = rulesHelper.getProperty(SOFT_SOUNDS);
        boolean isItLastLetterInWord = null == nextSymbols || !cyrillicAlphabet.containsKey(nextSymbols);
        if (isItLastLetterInWord && softSounds.contains(previousSymbol)) {
            return specificLetters.getProperty(currentSymbol);
        }

        if (hasSoftSymbols) {
            return specificLetters.getProperty(currentSymbol);
        }

        return cyrillicAlphabet.getProperty(currentSymbol);
    }
}
