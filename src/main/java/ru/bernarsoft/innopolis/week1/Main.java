package ru.bernarsoft.innopolis.week1;

import ru.bernarsoft.innopolis.week1.parsers.WordParser;
import ru.bernarsoft.innopolis.week1.parsers.ResourceParser;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * @author Alexander Sobolev
 * Проект реализован на основе многопоточности.
 * Вывод слов реализован с помощью логирования.
 * При нахождении повтора слова бросается исключение.
 *
 * Класс Main получает список ресурсов и запускает потоки на выполнение.
 */
public class Main {

    private static final String ROOT_NAME = "data/";
    private static final String RESOURCE_FILE = "resources.txt";
    private volatile static  Boolean checker = true;
    private final static Object lock = new Object();
    private volatile static HashSet<String> uniqWords = new HashSet<String>();

    public static void main(String[] args) throws InterruptedException {

        ArrayList<String> resources = ResourceParser.parseResourcesFromFile(ROOT_NAME + RESOURCE_FILE);
        for (String resource : resources) {
            String fullResourceName = ROOT_NAME + resource;
            new Thread(new WordParser(fullResourceName, uniqWords, lock)).start();
        }
    }

    public static void setCheckerFalse() {
        checker = false;
    }

    /**
     * Используем чтобы остановить выполнение потоков, если получили ошибку
     * @return возвращает false если произошла ошибка
     */
    public static Boolean isChecker() {
        return checker;
    }
}
