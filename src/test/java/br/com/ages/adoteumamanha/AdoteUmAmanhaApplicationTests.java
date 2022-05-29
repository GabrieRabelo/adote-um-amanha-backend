package br.com.ages.adoteumamanha;

import br.com.ages.adoteumamanha.service.pedidos.BuscarPedidoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AdoteUmAmanhaApplicationTests {

    @Autowired
    BuscarPedidoService buscarPedidoService;

    @Test
    void contextLoads() {
        assertThat(buscarPedidoService).isNotNull();
    }

}
