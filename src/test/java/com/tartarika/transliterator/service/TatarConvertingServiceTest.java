package com.tartarika.transliterator.service;

import com.tartarika.transliterator.exceptions.SourceTextIsNullException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * GeneralAlphabetTestService.
 *
 * @author Aydar_Safiullin
 */
public class TatarConvertingServiceTest {
    private LatinService testedService;

    @BeforeEach
    public void init() {
        testedService = new TatarConvertingService();
    }

    @Test
    public void testFailConvertingToLatinByNullSourceText() {
        String sourceText = null;
        assertThrows(SourceTextIsNullException.class, () -> testedService.convertToLatin(null));
    }

    @Test
    public void testFailConvertingToCyrillicByNullSourceText() {
        String sourceText = null;
        assertThrows(SourceTextIsNullException.class, () -> testedService.convertToCyrillic(null));
    }

    @Test
    public void testIsCyrillicTextWithoutSpecificLettersConvertingCorrect() {
        String sourceText = "Матур бала ";
        String expectedText = "Matur bala ";
        String resultText = testedService.convertToLatin(sourceText);
        assertEquals(expectedText, resultText);
    }

    @Test
    public void testIsCyrillicTextWithoutSpecificLettersConvertingIncorrect() {
        String sourceText = "Матур bala";
        String expectedText = "matur bala";
        String resultText = testedService.convertToLatin(sourceText);
        assertNotEquals(expectedText, resultText);
    }

    @Test
    public void testIsVLetterContainsCyrillicTestConvertingCorrect() {
        String sourceText = "Вакыт авылда тиз бара";
        String expectedText = "Wakıt awılda tiz bara";
        String resultText = testedService.convertToLatin(sourceText);
        assertEquals(expectedText, resultText);
    }

    @Test
    public void testIsVLetterContainsCyrillicTestConvertingIncorrect() {
        String sourceText = "Вакыт авылда тиз бара";
        String expectedText = "Vakıt avılda tiz bara";
        String resultText = testedService.convertToLatin(sourceText);
        assertNotEquals(expectedText, resultText);
    }

    @Test
    public void testIsLatinTextWithoutSpecificLetersConvertingCorrect() {
        String sourceText = "Matur bala ";
        String expectedText = "Матур бала ";
        String resultText = testedService.convertToCyrillic(sourceText);
        assertEquals(expectedText, resultText);
    }

    @Test
    public void testIsLatinTextWithoutSpecificLettersConvertingIncorrect() {
        String sourceText = "matur bala";
        String expectedText = "Матур bala";
        String resultText = testedService.convertToCyrillic(sourceText);
        assertNotEquals(expectedText, resultText);
    }

}
