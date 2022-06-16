package br.com.ages.adoteumamanha.service.usuarios;

import br.com.ages.adoteumamanha.domain.entity.Endereco;
import br.com.ages.adoteumamanha.domain.entity.Usuario;
import br.com.ages.adoteumamanha.domain.enumeration.Perfil;
import br.com.ages.adoteumamanha.domain.enumeration.Status;
import br.com.ages.adoteumamanha.dto.response.InformacaoUsuarioResponse;
import br.com.ages.adoteumamanha.dto.response.ResumoUsuariosResponse;
import br.com.ages.adoteumamanha.dto.response.UsuarioResponse;
import br.com.ages.adoteumamanha.fixture.Fixture;
import br.com.ages.adoteumamanha.mapper.CasaDescricaoResponseMapper;
import br.com.ages.adoteumamanha.mapper.InformacaoUsuarioResponseMapper;
import br.com.ages.adoteumamanha.mapper.ResumoUsuariosResponseMapper;
import br.com.ages.adoteumamanha.mapper.UsuarioResponseMapper;
import br.com.ages.adoteumamanha.repository.PedidoRepository;
import br.com.ages.adoteumamanha.repository.UsuarioRepository;
import br.com.ages.adoteumamanha.service.usuarios.BuscarUsuarioService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static br.com.ages.adoteumamanha.exception.Mensagem.CASA_NAO_ENCONTRADA;
import static br.com.ages.adoteumamanha.exception.Mensagem.USUARIO_NAO_ENCONTRADO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BuscarUsuarioServiceTest {

    @InjectMocks
    private BuscarUsuarioService service;

    @Mock
    private UsuarioRepository repository;
    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private CasaDescricaoResponseMapper casaDescricaoResponseMapper;

    @Mock
    private UsuarioResponseMapper usuarioResponseMapper;

    @Mock
    private InformacaoUsuarioResponseMapper informacaoUsuarioResponseMapper;

    @Mock
    private ResumoUsuariosResponseMapper resumoUsuariosResponseMapper;

    @Test
    public void buscar_casa_ok() {

        long id = 1;
        var usuario = Fixture.make(Usuario.builder()).build();

        when(repository.findByIdAndPerfil(id, Perfil.CASA)).thenReturn(Optional.of(usuario));
        when(casaDescricaoResponseMapper.apply(usuario)).thenCallRealMethod();

        service.buscarCasaPorId(id);

        verify(repository).findByIdAndPerfil(id, Perfil.CASA);
        verify(casaDescricaoResponseMapper).apply(usuario);
    }

    @Test
    public void buscar_usuario_ok() {

        long id = 1;
        var usuario = Fixture.make(Usuario.builder()).build();

        when(repository.findById(id)).thenReturn(Optional.of(usuario));
        when(usuarioResponseMapper.apply(usuario)).thenCallRealMethod();

        var result = service.buscarUsuarioPorId(id);

        var expectedResult = UsuarioResponse.builder()
                .withNome(usuario.getNome())
                .withPerfil(usuario.getPerfil())
                .build();

        assertThat(result).usingRecursiveComparison().isEqualTo(expectedResult);

        verify(repository).findById(id);
        verify(usuarioResponseMapper).apply(usuario);
    }

    @Test
    public void buscar_informacoes_usuario_por_id_ok() {

        long id = 1;
        var usuario = Fixture.make(Usuario.builder()).build();

        when(pedidoRepository.findNumberByIdUsuarioAndTipoPedidoAndStatus(id, Status.FINALIZADA)).thenReturn(2);
        when(pedidoRepository.findNumberByIdUsuarioAndTipoPedidoAndStatus(id, Status.RECUSADO)).thenReturn(1);

        when(repository.findById(id)).thenReturn(Optional.of(usuario));
        when(informacaoUsuarioResponseMapper.apply(usuario, 2, 1)).thenCallRealMethod();

        var result = service.buscarInformacoesUsuarioPorId(id);

        var expectedResult = InformacaoUsuarioResponse.builder()
                .withNome(usuario.getNome())
                .withDocumento(usuario.getDocumento())
                .withEmail(usuario.getEmail())
                .withTelefone(usuario.getTelefone())
                .withPerfil(usuario.getPerfil())
                .withEndereco(usuario.getEndereco())
                .withDoacoesAprovadas(2)
                .withDoacoesRecusadas(1)
                .build();

        assertThat(result).usingRecursiveComparison().isEqualTo(expectedResult);

        verify(repository).findById(id);
        verify(informacaoUsuarioResponseMapper).apply(usuario, 2, 1);
    }

    @Test
    public void buscar_informacoes_usuario_por_id_erro() {

        try {
            service.buscarInformacoesUsuarioPorId(null);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains(USUARIO_NAO_ENCONTRADO.getDescricao()));
            verifyNoInteractions(informacaoUsuarioResponseMapper);
        }
    }

    @Test
    public void casa_nao_encontrada_erro() {

        try {
            service.buscarCasaPorId(null);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains(CASA_NAO_ENCONTRADA.getDescricao()));
            verifyNoInteractions(casaDescricaoResponseMapper);
        }
    }

    @Test
    public void usuario_nao_encontrado_erro() {

        try {
            service.buscarUsuarioPorId(null);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains(USUARIO_NAO_ENCONTRADO.getDescricao()));
            verifyNoInteractions(usuarioResponseMapper);
        }
    }

    @Test
    public void buscar_lista_de_doadores() {
        var page = Fixture.make(PageRequest.class);
        var name = Fixture.make(String.class);

        var usuario = Fixture.make(Usuario.class);
        var userPage = new PageImpl<>(List.of(usuario));
        var response = Fixture.make(ResumoUsuariosResponse.class);

        when(repository.findAllDoadorComFiltro(name, page)).thenReturn(userPage);
        when(resumoUsuariosResponseMapper.apply(userPage)).thenReturn(response);

        service.buscarDoadores(name, page);

        verify(repository).findAllDoadorComFiltro(any(), any());
        verify(resumoUsuariosResponseMapper).apply(any());

    }
}