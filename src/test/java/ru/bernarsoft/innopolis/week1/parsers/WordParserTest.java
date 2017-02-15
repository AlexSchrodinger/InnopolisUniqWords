package ru.bernarsoft.innopolis.week1.parsers;

import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;


class WordParserTest {
    @Test
    void checkUniqWords() {

        HashSet<String> uniqWords = new HashSet<>();
        String line = "антон петр ксюша антон";
        Object lock = new Object();

        Throwable thrown = assertThrows(IllegalArgumentException.class, () ->
        {WordParser.checkWords(uniqWords, line, lock);
        });
        assertEquals(thrown.getMessage(), "Найден дубликат слова!");
    }

    @Test
    void checkEnglishWords() {

        HashSet<String> uniqWords = new HashSet<>();
        String line = "anton petr ksusha";
        Object lock = new Object();

        Throwable thrown = assertThrows(IllegalArgumentException.class, () ->
        {WordParser.checkWords(uniqWords, line, lock);
        });
        assertEquals(thrown.getMessage(), "Английские слова не допустимы!");
    }
}