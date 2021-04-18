package com.tartarika.transliterator.utils;

import com.tartarika.transliterator.lang.AppLanguage;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import static com.tartarika.transliterator.utils.PropertiesFile.*;

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
    public static Properties getLatinAlphabet(AppLanguage language) {
        return loadProperties(language, LATIN_ALPHABET);
    }

    /**
     * Cyrillic - latin letters matching file getting method.
     * @return file with cyrillic letters.
     */
    public static Properties getCyrillicAlphabet(AppLanguage language) {
        return loadProperties(language, CYRILLIC_ALPHABET);
    }

    /**
     * Specific letters matching file getting method.
     * @return file with specific letters.
     */
    public static Properties getSpecificLetters(AppLanguage language) {
        return loadProperties(language, SPECIFIC_LETTERS);
    }

    /**
     * Rules helper for choice specific letters file getting method.
     * @return file, helped to choice specific letters.
     */
    public static Properties getRulesHelperProperties(AppLanguage language) {
        return loadProperties(language, RULES_HELPER);
    }

    /**
     * Fill properties from files for chosen language.
     * @param language - service language.
     * @param property - needed property.
     * @return filled properties.
     */
    private static Properties loadProperties(AppLanguage language, PropertiesFile property) {
        Properties result = new Properties();
        File propertiesFile = getFile(language, property);
        try(FileReader propertiesReader = new FileReader(propertiesFile)) {
            result.load(propertiesReader);
        } catch (IOException e) {
            // TODO: 03.04.2021 handle this exception + may be add a PropertiesNotFoundException?
        }

        return result;
    }

    /**
     * Get needed properties file.
     * @param language - service language.
     * @param property - needed property.
     * @return needed properties file.
     */
    private static File getFile(AppLanguage language, PropertiesFile property) {
        String propertyName = language.getValue() + property.getValue();
        String propertyPath = FILE_PROPERTIES.getProperty(propertyName);
        if (StringUtils.isEmpty(propertyPath)) {
            // TODO: 03.04.2021 what doing if property not found?
        }
        return new File(RESOURCE_PATH + propertyPath);
    }
}
