package com.tartarika.transliterator.utils;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * AlphabetUtils.
 * App main utils for work with alphabets.
 *
 * @author Aydar_Safiullin
 */
public enum AlphabetUtils {
    ALPHABET_UTILS;

    private static Properties latinAlphabet;
    private static Properties cyrillicAlphabet;

    static {
        try(FileReader latinAlphabetReader = new FileReader(FileUtils.getLatinAlphabet());
            FileReader cyrillicAlphabetReader = new FileReader(FileUtils.getCyrillicAlphabet())) {

            latinAlphabet = new Properties();
            cyrillicAlphabet = new Properties();

            latinAlphabet.load(latinAlphabetReader);
            cyrillicAlphabet.load(cyrillicAlphabetReader);

        } catch (IOException e) {
            // TODO: 03.04.2021 handle this exception + may be add a PropertiesNotFoundException?
        }
    }

}
