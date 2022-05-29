package br.com.ages.adoteumamanha;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
class AdoteUmAmanhaApplicationTests {

    @Test
    void contextLoads() {
        new AdoteUmAmanhaApplication().main(new String[]{"--spring.profiles.active=test"});
    }

}
