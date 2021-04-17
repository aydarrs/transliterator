package com.tartarika.transliterator.service;

import com.tartarika.transliterator.exceptions.SourceTextIsNullException;
import com.tartarika.transliterator.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * GeneralAlphabetService.
 * App main service for work with alphabets.
 *
 * @author Aydar_Safiullin
 */
@Component
public class GeneralAlphabetService {
    private Properties latinAlphabet;
    private Properties cyrillicAlphabet;
    private TatarConvertingService specificLettersService;

    @Autowired
    public GeneralAlphabetService(@Qualifier("Tatar") TatarConvertingService service) {
        this.specificLettersService = service;
    }

    /**
     * Convert cyrillic text to latin.
     *
     * @param cyrillicText - original text.
     * @return converted latin text.
     */
    public String convertTextToLatinWriting(String cyrillicText) {
        return specificLettersService.convertToCyrillic(cyrillicText);
    }

    /**
     * Convert latin text to cyrillic.
     *
     * @param latinText - original text.
     * @return converted cyrillic text.
     */
    public String convertTextToCyrillicWriting(String latinText) {
        return specificLettersService.convertToCyrillic(latinText);
    }

}
