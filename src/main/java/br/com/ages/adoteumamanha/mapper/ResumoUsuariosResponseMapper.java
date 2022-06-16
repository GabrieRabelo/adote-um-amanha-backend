package br.com.ages.adoteumamanha.mapper;

import br.com.ages.adoteumamanha.domain.entity.Usuario;
import br.com.ages.adoteumamanha.dto.response.ResumoUsuarioResponse;
import br.com.ages.adoteumamanha.dto.response.ResumoUsuariosResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ResumoUsuariosResponseMapper implements Function<Page<Usuario>, ResumoUsuariosResponse> {

    final ResumoUsuarioResponseMapper resumoUsuarioResponseMapper;

    public ResumoUsuariosResponseMapper(ResumoUsuarioResponseMapper resumoUsuarioResponseMapper) {
        this.resumoUsuarioResponseMapper = resumoUsuarioResponseMapper;
    }

    @Override
    public ResumoUsuariosResponse apply(final Page<Usuario> usuarios) {

        return ResumoUsuariosResponse.builder()
                .withConteudo(mapContentToList(usuarios))
                .withNumeroDaPagina(usuarios.getNumber())
                .withNumeroDeElementos(usuarios.getNumberOfElements())
                .withPaginaVazia(usuarios.isEmpty())
                .withPrimeiraPagina(usuarios.isFirst())
                .withTamanhoDaPagina(usuarios.getSize())
                .withTotalDeElementos(usuarios.getTotalElements())
                .withTotalDePaginas(usuarios.getTotalPages())
                .withUltimaPagina(usuarios.isLast())
                .build();
    }

    public List<ResumoUsuarioResponse> mapContentToList(final Page<Usuario> usuarios) {
        return Optional.of(usuarios.getContent())
                .orElseGet(Collections::emptyList)
                .stream()
                .map(resumoUsuarioResponseMapper)
                .collect(Collectors.toList());
    }

}


