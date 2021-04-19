package com.tartarika.transliterator.service;

import com.tartarika.transliterator.exceptions.SourceTextIsNullException;
import com.tartarika.transliterator.service.latin.ILatinService;
import com.tartarika.transliterator.service.latin.tatar.TatarConvertingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TatarConvertingServiceTest.
 *
 * @author Aydar_Safiullin
 */
public class TatarConvertingServiceTest {
    private ILatinService testedService;

    @BeforeEach
    public void init() {
        testedService = new TatarConvertingService();
    }

    // General tests
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

    // Cyrillic text converting tests
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
    public void testIsCyrillicVLetterContainsTextConvertingCorrect() {
        String sourceText = "Вакыт авылда тиз бара";
        String expectedText = "Waqıt awılda tiz bara";
        String resultText = testedService.convertToLatin(sourceText);
        assertEquals(expectedText, resultText);
    }

    @Test
    public void testIsCyrillicVLetterContainsTextConvertingIncorrect() {
        String sourceText = "Вакыт авылда тиз бара";
        String expectedText = "Vaqıt avılda tiz bara";
        String resultText = testedService.convertToLatin(sourceText);
        assertNotEquals(expectedText, resultText);
    }

    @Test
    public void testIsCyrillicGLetterContainsTextConvertingCorrect() {
        String sourceText = "Галим гөмбә";
        String expectedText = "Ğalim gömbä";
        String resultText = testedService.convertToLatin(sourceText);
        assertEquals(expectedText, resultText);
    }

    @Test
    public void testIsCyrillicGLetterContainsTextConvertingIncorrect() {
        String sourceText = "Галим гөмбә";
        String expectedText = "Galim gömbä";
        String resultText = testedService.convertToLatin(sourceText);
        assertNotEquals(expectedText, resultText);
    }

    @Test
    public void testIsCyrillicELetterContainsTextConvertingCorrect() {
        String sourceText = "Елан ел быел";
        String expectedText = "Yelan yel bıel";
        String resultText = testedService.convertToLatin(sourceText);
        assertEquals(expectedText, resultText);
    }

    @Test
    public void testIsCyrillicELetterContainsTextConvertingIncorrect() {
        String sourceText = "Елан ел быел";
        String expectedText = "Yelan yel bıyel";
        String resultText = testedService.convertToLatin(sourceText);
        assertNotEquals(expectedText, resultText);
    }

    @Test
    public void testIsCyrillicKLetterContainsTextConvertingCorrect() {
        String sourceText = "Калим көмбә";
        String expectedText = "Qalim kömbä";
        String resultText = testedService.convertToLatin(sourceText);
        assertEquals(expectedText, resultText);
    }

    @Test
    public void testIsCyrillicKLetterContainsTextConvertingIncorrect() {
        String sourceText = "Калим көмбә";
        String expectedText = "Kalim kömbä";
        String resultText = testedService.convertToLatin(sourceText);
        assertNotEquals(expectedText, resultText);
    }

    // Latin text converting tests

    @Test
    public void testIsLatinTextWithoutSpecificLettersConvertingCorrect() {
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
