package br.com.ages.adoteumamanha.util;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class TokenGeneratorUtils {

    public String generate() {

        final Random rnd = new Random();
        final Integer number = rnd.nextInt(999999999);

        return String.format("%06d", number);
    }
}
