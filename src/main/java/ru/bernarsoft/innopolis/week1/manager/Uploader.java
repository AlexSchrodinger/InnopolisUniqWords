package ru.bernarsoft.innopolis.week1.manager;


import java.util.HashSet;

import static ru.bernarsoft.innopolis.week1.Main.isChecker;
import static ru.bernarsoft.innopolis.week1.Main.setCheckerFalse;

/**
 * Класс Uploader выгружает слова из буфера и проверяет на уникальность каждое слово.
 */
public class Uploader implements Runnable {

    private BufferOfWords bufferOfWords;
    private HashSet<String> uniqWords = new HashSet<>();

    public Uploader(BufferOfWords bufferOfWords) {
        this.bufferOfWords = bufferOfWords;
    }

    @Override
    public void run() {
        try {
            String word = bufferOfWords.get();
            while (word != null && isChecker()) {
                if(uniqWords.add(word)) {
                    System.out.println(word);
                    word = bufferOfWords.get();
                }
                else {
                    setCheckerFalse();
                    System.out.println("Стоп! Повтор слова - " + word);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
