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
public class GeneralAlphabetTestService {
    private GeneralAlphabetService testedService;

    @BeforeEach
    void init() {
        SpecificTatarLettersService helperService = new SpecificTatarLettersService();
        testedService = new GeneralAlphabetService(helperService);
    }

    @Test
    public void testFailConvertingToLatinByNullSourceText() {
        String sourceText = null;
        assertThrows(SourceTextIsNullException.class, () -> testedService.convertTextToLatinWriting(null));
    }

    @Test
    public void testFailConvertingToCyrillicByNullSourceText() {
        String sourceText = null;
        assertThrows(SourceTextIsNullException.class, () -> testedService.convertTextToCyrillicWriting(null));
    }

    @Test
    public void testIsCyrillicTextWithoutSpecificLettersConvertingCorrect() {
        String sourceText = "Матур бала ";
        String expectedText = "Matur bala";
        String resultText = testedService.convertTextToLatinWriting(sourceText);
        assertEquals(expectedText, resultText);
    }

    @Test
    public void testIsCyrillicTextWithoutSpecificLettersFailConvertingIncorrect() {
        String sourceText = "Матур bala";
        String expectedText = "matur bala";
        String resultText = testedService.convertTextToLatinWriting(sourceText);
        assertNotEquals(expectedText, resultText);
    }

    @Test
    public void testIsVLetterContainsCyrillicTestConvertingCorrect() {
        String sourceText = "Вакыт авылда тиз бара";
        String expectedText = "Wakıt awılda tiz bara";
        String resultText = testedService.convertTextToLatinWriting(sourceText);
        assertEquals(expectedText, resultText);
    }

    @Test
    public void testIsVLetterContainsCyrillicTestConvertingIncorrect() {
        String sourceText = "Вакыт авылда тиз бара";
        String expectedText = "Vakıt avılda tiz bara";
        String resultText = testedService.convertTextToLatinWriting(sourceText);
        assertNotEquals(expectedText, resultText);
    }

}
