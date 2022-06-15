package br.com.ages.adoteumamanha.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class TokenGeneratorUtilsTest {

    @InjectMocks
    private TokenGeneratorUtils utils;

    @Test
    public void ok() {
        final String resultado = utils.generate();

        assertEquals(resultado.length(), 9);
        assertTrue(resultado.matches("[0-9]+"));
    }

}