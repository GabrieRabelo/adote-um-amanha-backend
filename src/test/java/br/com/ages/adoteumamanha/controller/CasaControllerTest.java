package br.com.ages.adoteumamanha.controller;

import br.com.ages.adoteumamanha.dto.response.CasaDescricaoResponse;
import br.com.ages.adoteumamanha.service.CasaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CasaControllerTest {

    @Autowired
    private MockMvc client;

    @MockBean
    private CasaService casaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "doador", roles = "DOADOR")
    void testGetCasaDetalhe() throws Exception {

        var casaDescricao = CasaDescricaoResponse.builder().build();

        when(casaService.buscaCasaDescricao(1L)).thenReturn(casaDescricao);

        var resultString = client.perform(MockMvcRequestBuilders.get("/public/casas/1"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        var resultPartner = objectMapper.readValue(resultString, CasaDescricaoResponse.class);

        var expectedPartner = CasaDescricaoResponse.builder().build();

        assertThat(resultPartner).usingRecursiveComparison().isEqualTo(expectedPartner);

        verify(casaService).buscaCasaDescricao(anyLong());
    }

}