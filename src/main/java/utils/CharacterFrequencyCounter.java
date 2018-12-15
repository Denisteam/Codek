package utils;

import java.io.*;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.util.WeakHashMap;
import java.util.concurrent.locks.ReadWriteLock;

public class CharacterFrequencyCounter {
    private Reader reader;

    public CharacterFrequencyCounter(Reader reader) {
        this.reader = reader;
    }

    public CharacterFrequencyCounter(String source) {
        this.reader = new StringReader(source);
    }

    public CharacterFrequencyCounter(char[] chars) throws IOException {
        this.reader = new CharArrayReader(chars);
        count();
    }

    public Map<Character, Integer> count() throws IOException {
        Map<Character, Integer> map = new TreeMap<>();

        int r;
        while ((r = reader.read()) != -1) {
            Integer old = map.getOrDefault((char)r, 0);
            map.put((char)r, old + 1);
        }
        reader.close();

        return map;
    }
}
