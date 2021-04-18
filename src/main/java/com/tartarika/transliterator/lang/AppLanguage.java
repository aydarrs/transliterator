package com.tartarika.transliterator.lang;

/**
 * AppLanguage.
 * Application languages.
 * @author Aydar_Safiullin
 */
public enum AppLanguage {
    TATAR("Tatar");

    private final String value;

    AppLanguage(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
