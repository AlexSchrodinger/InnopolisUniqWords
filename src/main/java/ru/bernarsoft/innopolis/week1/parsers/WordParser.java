package ru.bernarsoft.innopolis.week1.parsers;


import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ru.bernarsoft.innopolis.week1.Main.isChecker;
import static ru.bernarsoft.innopolis.week1.Main.setCheckerFalse;

/**
 * Класс WordParser в потоке загружает слова из файла и проверяет каждое слово на уникальность.
 */

public class WordParser implements Runnable{

    private static Logger logger = Logger.getLogger("WordParser");

    private String resource;
    private HashSet<String> uniqWords;
    private volatile Object lock;

    public WordParser(String resource, HashSet<String> uniqWords, Object lock) {
        this.resource = resource;
        this.uniqWords = uniqWords;
        this.lock = lock;
    }

    @Override
    public void run() {

        String stringLine = null;
        try {
            FileInputStream fis = new FileInputStream(resource);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            stringLine = reader.readLine();

            while (stringLine != null) {
                checkWords(uniqWords, stringLine, lock);
                stringLine = reader.readLine();
            }

        } catch (IOException e) {
            logger.error("Ошибка чтения файла - " + e.getMessage());
        }
    }

    /**
     * Метод checkWords разбивает строку на слова, удаляет цифры и знаки препинания.
     * Полученные слова проверяет на уникальность и анлийский язык.
     * @param uniqWords HashSet в который будем писать уникальные слова
     * @param line строка прочитанная из файла
     * @param lock объект для синхронизации
     */

    public static void checkWords(HashSet<String> uniqWords, String line, Object lock) {
        String[] words = (line + " ").split("\\p{P}?[ \\t\\n\\r\\d]+");
        Pattern p = Pattern.compile("[A-Za-z]");
        for (String word : words) {
            if(isChecker()) {
                Matcher m = p.matcher(word);
                if (!m.find()) {
                    synchronized (lock) {
                        if (uniqWords.add(word)) {
                            logger.info(word);
                        } else {
                            setCheckerFalse();
                            logger.info(word);
                            throw new IllegalArgumentException("Найден дубликат слова!");
                        }
                    }
                } else {
                    setCheckerFalse();
                    throw new IllegalArgumentException("Английские слова не допустимы!");
                }
            } else {
                return;
            }
        }
    }
}
