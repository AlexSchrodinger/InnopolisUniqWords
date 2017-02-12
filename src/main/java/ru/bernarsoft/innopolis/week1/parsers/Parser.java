package ru.bernarsoft.innopolis.week1.parsers;


import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ru.bernarsoft.innopolis.week1.Main.setCheckerFalse;

/**
 * Класс Parser парсит файлы и передает результат
 */
public class Parser {

    private static ArrayList<String> wordsFromFile = new ArrayList<>();

    /**
     * Метод parseWordsFromFile читает файл построчно, очищает от спец. символов и цифр,
     * проверяет на английский язык, разделяет строку на слова и пошещает в массив строк.
     * @param fileName имя файла
     * @return возвращает список слов после обработки
     */
    public static ArrayList<String> parseWordsFromFile(String fileName) {
        wordsFromFile.clear();
        String stringLine = null;
        try {
            FileInputStream fis = new FileInputStream(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            stringLine = reader.readLine();

            while (stringLine != null) {
                checkWords(stringLine);
                stringLine = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wordsFromFile;
    }

    /**
     * Метод parseResourcesFromFile разделяет строку на слова и пошещает в массив строк.
     * @param fileName имя файла назначения
     * @return возвращает список ресурсов
     */
    public static ArrayList<String> parseResourcesFromFile(String fileName) {
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
            e.printStackTrace();
        }
        return wordsFromFile;
    }

    /**
     * Разбиение строки на слова
     * @param line строка из файла
     */
    private static void splitWords(String line) {
        String[] words = (line + " ").split("[\\s]+");
        Collections.addAll(wordsFromFile, words);
    }

    /**
     * Разбиение строки на слова, очистка и проверка на английский
     * @param line строка из файла
     */
    private static void checkWords(String line) {
        String[] words = (line + " ").split("\\p{P}?[ \\t\\n\\r\\d]+");
        Pattern p = Pattern.compile("[A-Za-z]");
        for (String word : words) {
            Matcher m = p.matcher(word);
            if(!m.find()) {
                Collections.addAll(wordsFromFile, words);
            }
            else {
                setCheckerFalse();
                System.out.println("Английские слова не допустимы - " + word);
        }
    }

    }
}
