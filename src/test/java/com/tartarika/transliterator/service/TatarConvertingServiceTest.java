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
        String sourceText = "Ава В вд Ви";
        String expectedText = "Awa V vd Wi";
        String resultText = testedService.convertToLatin(sourceText);
        assertEquals(expectedText, resultText);
    }

    @Test
    public void testIsCyrillicVLetterContainsTextConvertingIncorrect() {
        String sourceText = "Ава В вд Ви";
        String expectedText = "Ava V vd Vi";
        String resultText = testedService.convertToLatin(sourceText);
        assertNotEquals(expectedText, resultText);
    }

    @Test
    public void testIsCyrillicGLetterContainsTextConvertingCorrect() {
        String sourceText = "Рги рга Га Г иго";
        String expectedText = "Rgi rğa Ğa G iğo";
        String resultText = testedService.convertToLatin(sourceText);
        assertEquals(expectedText, resultText);
    }

    @Test
    public void testIsCyrillicGLetterContainsTextConvertingIncorrect() {
        String sourceText = "Рги рга Га Г иго";
        String expectedText = "Rgi rga Ga G igo";
        String resultText = testedService.convertToLatin(sourceText);
        assertNotEquals(expectedText, resultText);
    }

    @Test
    public void testIsCyrillicELetterContainsTextConvertingCorrect() {
        String sourceText = "Ела аел бел";
        String expectedText = "Yela ayel bel";
        String resultText = testedService.convertToLatin(sourceText);
        assertEquals(expectedText, resultText);
    }

    @Test
    public void testIsCyrillicELetterContainsTextConvertingIncorrect() {
        String sourceText = "Ела аел бел";
        String expectedText = "Yela ayel byel";
        String resultText = testedService.convertToLatin(sourceText);
        assertNotEquals(expectedText, resultText);
    }

    @Test
    public void testIsCyrillicKLetterContainsTextConvertingCorrect() {
        String sourceText = "Рки рка Ка К ико икр ик Ок окл";
        String expectedText = "Rki rqa Qa K iqo ikr ik Oq oql";
        String resultText = testedService.convertToLatin(sourceText);
        assertEquals(expectedText, resultText);
    }

    @Test
    public void testIsCyrillicKLetterContainsTextConvertingIncorrect() {
        String sourceText = "Рки рка Ка К ико икр ик Ок окл";
        String expectedText = "Rki rka Ka K iko ikr ik Ok okl";
        String resultText = testedService.convertToLatin(sourceText);
        assertNotEquals(expectedText, resultText);
    }

    @Test
    public void testIsCyrillicYuLetterContainsTextConvertingCorrect() {
        String sourceText = "Юл юлти Юлта юти Июли ию Аю";
        String expectedText = "Yul yülti Yulta yüti İyüli iyü Ayu";
        String resultText = testedService.convertToLatin(sourceText);
        assertEquals(expectedText, resultText);
    }

    @Test
    public void testIsCyrillicYuLetterContainsTextConvertingIncorrect() {
        String sourceText = "Юл юлти Юлта юти Июли ию Аю";
        String expectedText = "Yul yulti Yulta yuti İyuli iyu Ayu";
        String resultText = testedService.convertToLatin(sourceText);
        assertNotEquals(expectedText, resultText);
    }

    @Test
    public void testIsCyrillicYaLetterContainsTextConvertingCorrect() {
        String sourceText = "Ял ялти Ялта яти Ияли ия Ая";
        String expectedText = "Yal yälti Yalta yäti İyäli iyä Aya";
        String resultText = testedService.convertToLatin(sourceText);
        assertEquals(expectedText, resultText);
    }

    @Test
    public void testIsCyrillicYaLetterContainsTextConvertingIncorrect() {
        String sourceText = "Ял ялти Ялта яти Ияли ия Ая";
        String expectedText = "Yal yalti Yalta yati İyali iya Aya";
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
