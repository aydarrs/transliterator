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
            String currentSymbol = symbols.get(i);
            String previousSymbol = null;
            String nextSymbol = null;

            if (currentSymbol.equalsIgnoreCase("в")) {
                if (i < symbols.size() - 1) {
                    nextSymbol = symbols.get(i + 1);
                }
                String latinV = convertV(currentSymbol, nextSymbol);
                joiner.add(latinV);
                continue;
            }

            if (currentSymbol.equalsIgnoreCase("к") || currentSymbol.equalsIgnoreCase("г")) {
                if (i != 0) {
                    previousSymbol = symbols.get(i - 1);
                }
                if (i < symbols.size() - 1) {
                    nextSymbol = symbols.get(i + 1);
                }
                String latinGorK = convertGAndK(previousSymbol, currentSymbol, nextSymbol);
                joiner.add(latinGorK);
                continue;
            }

            if (currentSymbol.equalsIgnoreCase("е")) {
                if (i != 0) {
                    previousSymbol = symbols.get(i - 1);
                }
                String latinE = convertE(previousSymbol, currentSymbol);
                joiner.add(latinE);
                continue;
            }

            String latinSymbol = cyrillicAlphabet.getProperty(currentSymbol);
            joiner.add(Objects.requireNonNullElse(latinSymbol, currentSymbol));
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
     * Convert cyrillic "В" letter to latin by rules of Tatar language.
     *
     * @param currentSymbol - current symbol;
     * @param nextSymbol - next symbol;
     * @return converted symbol.
     */
    private String convertV(String currentSymbol, String nextSymbol) {
        String vowels = rulesHelper.get("vowels").toString();

        if (null !=nextSymbol && vowels.contains(nextSymbol)) {
            return specificLetters.getProperty(currentSymbol);
        }

        return cyrillicAlphabet.getProperty(currentSymbol);
    }

    private String convertGAndK(String previousSymbol, String currentSymbol, String nextSymbol) {
        String softVowels = rulesHelper.get("softVowels").toString();
        String hardVowels = rulesHelper.get("hardVowels").toString();

        if (null != nextSymbol && hardVowels.contains(nextSymbol)) {
            return specificLetters.getProperty(currentSymbol);
        }

        if (null != nextSymbol && null != previousSymbol && !hardVowels.contains(nextSymbol) && hardVowels.contains(previousSymbol)) {
            return specificLetters.getProperty(currentSymbol);
        }

        if (null == nextSymbol && null != previousSymbol && hardVowels.contains(previousSymbol)) {
            return specificLetters.getProperty(currentSymbol);
        }

        return cyrillicAlphabet.getProperty(currentSymbol);
    }

    private String convertE(String previousSymbol, String currentSymbol) {
        String vowels = rulesHelper.get("vowels").toString();

        if (null != previousSymbol && vowels.contains(previousSymbol)) {
            return specificLetters.getProperty(currentSymbol);
        }

        return cyrillicAlphabet.getProperty(currentSymbol);
    }
}
