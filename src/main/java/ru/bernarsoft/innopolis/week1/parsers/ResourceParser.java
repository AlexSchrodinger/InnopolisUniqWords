package ru.bernarsoft.innopolis.week1.parsers;


import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Класс ResourceParser парсит файл с ресурсами и передает список ресурсов
 * в класс Main для запуска потоков.
 */
public class ResourceParser {

    private static Logger logger = Logger.getLogger("ResourceParser");
    private static ArrayList<String> resourcesFromFile = new ArrayList<>();


    /**
     * Метод parseResourcesFromFile парсит файл с ресурсами.
     * @param fileName имя файла с ресурсами
     * @return список имен ресурсов
     */
    public static  ArrayList<String> parseResourcesFromFile(String fileName) {

        String stringLine = null;
        try {
            FileInputStream fis = new FileInputStream(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            stringLine = reader.readLine();

            while (stringLine != null) {
                splitWords(stringLine);
                stringLine = reader.readLine();
            }
        } catch (IOException e) {
            logger.error("Ошибка чтения файла - " + e.getMessage());
        }
        return resourcesFromFile;
    }

    /**
     * Разбиение строки на слова
     * @param line строка из файла
     */
    public static void splitWords(String line) {
        String[] words = (line + " ").split("[\\s]+");
        Collections.addAll(resourcesFromFile, words);
    }
}
