package com.tartarika.transliterator.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * FileUtils.
 * Utils for work with files.
 *
 * @author Aydar_Safiullin
 */
public class FileUtils {
    protected static final Properties FILE_PROPERTIES;
    private static final String RESOURCE_PATH;

    static  {
        RESOURCE_PATH = "src\\main\\resources\\";
        String generalDirectoryPath = RESOURCE_PATH + "general\\directories.properties";
        File generalDirectoryFile = new File(generalDirectoryPath);
        FILE_PROPERTIES = new Properties();
        try (FileReader fr = new FileReader(generalDirectoryFile)) {
            FILE_PROPERTIES.load(fr);
        } catch (IOException e) {
            // TODO: 03.04.2021 handle this exception + may be add a PropertiesNotFoundException?
        }
    }

    private FileUtils() {}

    /**
     * Latin - cyrillic letters matching file getting method.
     * @return file with latin letters.
     */
    public static File getLatinAlphabet() {
        String latinAlphabetPath = FILE_PROPERTIES.getProperty("latinAlphabet");
        if (StringUtils.isEmpty(latinAlphabetPath)) {
            // TODO: 03.04.2021 what doing if property not found?
        }
        return new File(RESOURCE_PATH + latinAlphabetPath);
    }

    /**
     * Cyrillic - latin letters matching file getting method.
     * @return file with cyrillic letters.
     */
    public static File getCyrillicAlphabet() {
        String cyrillicAlphabetPath = FILE_PROPERTIES.getProperty("cyrillicAlphabet");
        if (StringUtils.isEmpty(cyrillicAlphabetPath)) {
            // TODO: 03.04.2021 what doing if property not found?
        }
        return new File(RESOURCE_PATH + cyrillicAlphabetPath);
    }
}
