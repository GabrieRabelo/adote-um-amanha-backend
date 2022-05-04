package br.com.ages.adoteumamanha.controller;

import br.com.ages.adoteumamanha.domain.enumeration.Perfil;
import br.com.ages.adoteumamanha.dto.response.CasaDescricaoResponse;
import br.com.ages.adoteumamanha.dto.response.UsuarioResponse;
import br.com.ages.adoteumamanha.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UsuarioControllerTest {

    @Autowired
    private MockMvc client;

    @MockBean
    private UsuarioService usuarioService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetCasaDetalhe() throws Exception {

        var casaDescricao = CasaDescricaoResponse.builder().build();

        when(usuarioService.buscaCasaDescricao(1L)).thenReturn(casaDescricao);

        var resultString = client.perform(MockMvcRequestBuilders.get("/public/casas/1"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        var resultPartner = objectMapper.readValue(resultString, CasaDescricaoResponse.class);

        var expectedPartner = CasaDescricaoResponse.builder().build();

        assertThat(resultPartner).usingRecursiveComparison().isEqualTo(expectedPartner);

        verify(usuarioService).buscaCasaDescricao(anyLong());
    }

    @Test
    @WithUserDetails(userDetailsServiceBeanName = "customUserDetailsService", value = "lar_esperanca@email.com")
    void testGetUsuarioAutenticacado() throws Exception {

        var usuarioEsperado = UsuarioResponse.builder()
                .withPerfil(Perfil.CASA)
                .withNome("lar esperanca")
                .build();

        when(usuarioService.buscaUsuario(9999L)).thenReturn(usuarioEsperado);

        var resultString = client.perform(MockMvcRequestBuilders.get("/usuario"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        var usuarioResponse = objectMapper.readValue(resultString, UsuarioResponse.class);

        assertThat(usuarioResponse).usingRecursiveComparison().isEqualTo(usuarioEsperado);

        verify(usuarioService).buscaUsuario(anyLong());
    }

}