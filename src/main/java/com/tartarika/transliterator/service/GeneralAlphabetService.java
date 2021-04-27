package com.tartarika.transliterator.service;

import com.tartarika.transliterator.service.latin.ILatinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * GeneralAlphabetService.
 * App main service for work with alphabets.
 *
 * @author Aydar_Safiullin
 */
@Component
public class GeneralAlphabetService {
    private ILatinService specificLettersService;

    @Autowired
    public GeneralAlphabetService(@Qualifier("Tatar") ILatinService service) {
        this.specificLettersService = service;
    }

    /**
     * Convert cyrillic text to latin.
     * @param cyrillicText - original text.
     * @return converted latin text.
     */
    public String convertTextToLatinWriting(String cyrillicText) {
        return specificLettersService.convertToLatin(cyrillicText);
    }

    /**
     * Convert latin text to cyrillic.
     * @param latinText - original text.
     * @return converted cyrillic text.
     */
    public String convertTextToCyrillicWriting(String latinText) {
        return specificLettersService.convertToCyrillic(latinText);
    }

}
