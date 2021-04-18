package com.tartarika.transliterator.utils;

/**
 * PropertiesFile
 *
 * @author Aydar_Safiullin
 */
public enum PropertiesFile {
    LATIN_ALPHABET("LatinAlphabet"),
    CYRILLIC_ALPHABET("CyrillicAlphabet"),
    RULES_HELPER("RulesHelper"),
    SPECIFIC_LETTERS("SpecificLetters");

    private String value;

    PropertiesFile(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
