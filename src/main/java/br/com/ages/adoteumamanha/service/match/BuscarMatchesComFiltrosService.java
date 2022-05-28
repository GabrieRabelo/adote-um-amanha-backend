package br.com.ages.adoteumamanha.service.match;

import br.com.ages.adoteumamanha.domain.entity.Match;
import br.com.ages.adoteumamanha.domain.enumeration.Categoria;
import br.com.ages.adoteumamanha.domain.enumeration.Direcao;
import br.com.ages.adoteumamanha.domain.enumeration.Status;
import br.com.ages.adoteumamanha.domain.enumeration.Subcategoria;
import br.com.ages.adoteumamanha.dto.response.MatchesResponse;
import br.com.ages.adoteumamanha.mapper.MatchesResponseMapper;
import br.com.ages.adoteumamanha.repository.MatchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static java.time.LocalTime.MIN;
import static java.util.Optional.ofNullable;
import static org.springframework.data.domain.Sort.by;

@Slf4j
@Service
@RequiredArgsConstructor
public class BuscarMatchesComFiltrosService {

    private final MatchRepository repository;
    private final MatchesResponseMapper matchesResponseMapper;

    public MatchesResponse buscar(final Integer pagina,
                                  final Integer tamanho, final String ordenacao,
                                  final Direcao direcao,
                                  final List<Categoria> categorias,
                                  final List<Subcategoria> subcategorias,
                                  final List<Status> status,
                                  final Integer mesesCorte,
                                  final String textoBusca) {


        final LocalDateTime mesesDeCorte = ofNullable(mesesCorte).map(meses -> LocalDateTime.now().minusMonths(meses).with(MIN)).orElse(null);
        log.debug("Buscando matches dados os seguintes parâmetros: categorias {}, subcategorias {}, status {}, dataCorte {}, texto de busca {}", categorias, subcategorias, status, mesesDeCorte, textoBusca);

        log.info("pagina {}, tamanho {}, ordenacao {}, direcao {}", pagina, tamanho, ordenacao, direcao);
        final Pageable paging = PageRequest.of(pagina, tamanho, by(Sort.Direction.valueOf(direcao.name()), ordenacao));

        log.info("Buscando no banco pedidos paginados");
        final Page<Match> matchesEntities = repository.findAllMatchesPorFiltro(categorias, subcategorias, mesesDeCorte, textoBusca, status, paging);


        return matchesResponseMapper.apply(matchesEntities);
    }
}