package br.com.ages.adoteumamanha.mapper;

import br.com.ages.adoteumamanha.domain.entity.Usuario;
import br.com.ages.adoteumamanha.dto.response.ResumoUsuarioResponse;
import br.com.ages.adoteumamanha.dto.response.ResumoUsuariosResponse;
import br.com.ages.adoteumamanha.fixture.Fixture;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ResumoUsuariosResponseMapperTest {

    @Mock
    private ResumoUsuarioResponseMapper resumoUsuarioResponseMapper;

    @InjectMocks
    private ResumoUsuariosResponseMapper resumoUsuariosResponseMapper;

    @Test
    void ok() {

        final Usuario usuario = Fixture.make(Usuario.builder()).build();
        final List<Usuario> usuariosResponse = Collections.singletonList(usuario);
        final Page<Usuario> pagedResponse = new PageImpl<>(usuariosResponse);

        var resumoUsuario = ResumoUsuarioResponse.builder().build();

        when(resumoUsuarioResponseMapper.apply(any())).thenReturn(resumoUsuario);

        final ResumoUsuariosResponse response = resumoUsuariosResponseMapper.apply(pagedResponse);

        Assertions.assertEquals(response.getConteudo().size(), pagedResponse.getContent().size());
    }
}