package com.liberty.common;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * User: Dimitr
 * Date: 18.05.2017
 * Time: 8:19
 */
public class RandomPicker {
    private static <E> List<E> pickNRandomElements(List<E> list, int n, Random r) {
        int length = list.size();

        if (length < n) return null;

        for (int i = length - 1; i >= length - n; --i) {
            Collections.swap(list, i, r.nextInt(i + 1));
        }
        return list.subList(length - n, length);
    }

    public static <E> List<E> pickNRandomElements(List<E> list, int n) {
        return pickNRandomElements(list, n, ThreadLocalRandom.current());
    }

    public static <K, V> V pickRandomElement(Map<K, List<V>> map) {
        List<V> vs = randEntryValue(map);
        return pickNRandomElements(vs, 1, new Random()).get(0);
    }

    private static <K, V> Map.Entry<K, V> randEntry(Iterator<Map.Entry<K, V>> it, int count) {
        int index = (int) (Math.random() * count);

        while (index > 0 && it.hasNext()) {
            it.next();
            index--;
        }

        return it.next();
    }

    private static <K, V> Map.Entry<K, V> randEntry(Set<Map.Entry<K, V>> entries) {
        return randEntry(entries.iterator(), entries.size());
    }

    private static <K, V> Map.Entry<K, V> randEntry(Map<K, V> map) {
        return randEntry(map.entrySet());
    }

    private static <K, V> K randEntryKey(Map<K, V> map) {
        return randEntry(map).getKey();
    }

    private static <K, V> V randEntryValue(Map<K, V> map) {
        return randEntry(map).getValue();
    }
}
