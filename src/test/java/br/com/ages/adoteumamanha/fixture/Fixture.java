package br.com.ages.adoteumamanha.fixture;

import org.jeasy.random.EasyRandom;

public class Fixture {

    private static final EasyRandom easyRandom = new EasyRandom();

    public static <T> T make(final T mockClass) {
        return (T) easyRandom.nextObject(mockClass.getClass());
    }

    public static <T> T make(final Class<T> clazz) {
        return (T) easyRandom.nextObject(clazz);
    }
}
