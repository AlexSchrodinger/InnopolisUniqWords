package ru.bernarsoft.innopolis.week1.manager;

import ru.bernarsoft.innopolis.week1.parsers.Parser;
import java.util.ArrayList;

/**
 * Класс Downloader в потоке загружает слова из файла в буфер.
 */
public class Downloader implements Runnable{

    private String resource;
    private BufferOfWords bufferOfWords;

    public Downloader(String resource, BufferOfWords bufferOfWords) {
        this.resource = resource;
        this.bufferOfWords = bufferOfWords;
    }

    @Override
    public void run() {
        ArrayList<String> words = Parser.parseWordsFromFile(resource);
        try {
            bufferOfWords.put(words);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
