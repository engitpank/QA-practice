package lesson6.util;

import com.github.javafaker.Faker;

import java.util.*;

public class RandomElement {
    public static <K, T> Map.Entry<K, T> fromMap(Map<K, List<T>> map) {
        if (map == null || map.isEmpty()) {
            throw new IllegalArgumentException("Map не может быть пустой или null");
        }

        Faker faker = new Faker();

        List<K> keys = new ArrayList<>(map.keySet());
        K randomKey = keys.get(faker.random().nextInt(keys.size()));

        List<T> values = map.get(randomKey);

        if (values == null || values.isEmpty()) {
            throw new IllegalArgumentException("Список значений для ключа " + randomKey + " пуст или null");
        }

        return new AbstractMap.SimpleEntry<>(randomKey, fromList(values));
    }

    public static <T> T fromList(List<T> list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("Список не может быть пустым или null");
        }
        Random random = new Random();
        return list.get(random.nextInt(list.size()));
    }
}