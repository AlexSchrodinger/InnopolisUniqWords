package ru.bernarsoft.innopolis.week1.manager;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import static ru.bernarsoft.innopolis.week1.Main.isChecker;

/**
 * Класс BufferOfWords работает буфером и реализует 2 синхронизированных метода: put - положить слова в очередь и
 * get - забрать слово из очереди.
 */
public class BufferOfWords {

    private Queue<String> stringQueue = new LinkedList<>();

    public synchronized void put(ArrayList<String> words) throws InterruptedException {
        while (stringQueue.peek() != null && isChecker()) {
            this.wait();
        }
        for(String word : words) {
            stringQueue.add(word);
        }
        this.notifyAll();
    }

    public synchronized String get() throws InterruptedException {
        while (stringQueue.peek() == null && isChecker()) {
            this.wait();
        }
            this.notifyAll();

            String result = stringQueue.poll();
            return result;
    }
}
