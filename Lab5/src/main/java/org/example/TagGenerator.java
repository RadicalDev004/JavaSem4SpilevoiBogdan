package org.example;

import java.util.List;
import java.util.Random;

public class TagGenerator {
    private static final List<String> TAGS = List.of(
            "nature", "portrait", "cityscape", "abstract", "night",
            "landscape", "black white", "art", "travel", "sunset"
    );

    private static final Random RANDOM = new Random();

    public static String getRandomTag() {
        return TAGS.get(RANDOM.nextInt(TAGS.size()));
    }

    public static List<String> getRandomTags(int count) {
        return RANDOM.ints(0, TAGS.size())
                .distinct()
                .limit(count)
                .mapToObj(TAGS::get)
                .toList();
    }

}
