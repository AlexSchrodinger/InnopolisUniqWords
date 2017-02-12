package ru.bernarsoft.innopolis.week1;

import ru.bernarsoft.innopolis.week1.manager.BufferOfWords;
import ru.bernarsoft.innopolis.week1.manager.Downloader;
import ru.bernarsoft.innopolis.week1.manager.Uploader;
import ru.bernarsoft.innopolis.week1.parsers.Parser;

import java.util.ArrayList;

/**
 * @author Alexander Sobolev
 * Проект реализован на основе паттерна многопоточности - Producer/Customer.
 * В проекте не импользуется пакет concurent, только связка synchronized/wait//notify.
 * Класс Main получает список ресурсов и запускает потоки на выполнение.
 */
public class Main {

    private static final String ROOT_NAME = "data/";
    private static final String RESOURCE_FILE = "resources.txt";
    private static  Boolean checker = true;

    public static void main(String[] args) throws InterruptedException {
        BufferOfWords bufferOfWords = new BufferOfWords();

        ArrayList<String> resources = Parser.parseResourcesFromFile(ROOT_NAME + RESOURCE_FILE);
        for (String resource : resources) {
            String fullResourceName = ROOT_NAME + resource;
            new Thread(new Downloader(fullResourceName, bufferOfWords)).start();
        }

        new Thread(new Uploader(bufferOfWords)).start();
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
