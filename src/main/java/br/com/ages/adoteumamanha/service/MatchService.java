package br.com.ages.adoteumamanha.service;

import br.com.ages.adoteumamanha.domain.entity.Match;
import br.com.ages.adoteumamanha.domain.entity.Pedido;
import br.com.ages.adoteumamanha.domain.entity.Usuario;
import br.com.ages.adoteumamanha.domain.enumeration.Direcao;
import br.com.ages.adoteumamanha.domain.enumeration.Status;
import br.com.ages.adoteumamanha.domain.enumeration.TipoPedido;
import br.com.ages.adoteumamanha.dto.request.AtualizarPedidoRequest;
import br.com.ages.adoteumamanha.dto.request.CadastrarPedidoRequest;
import br.com.ages.adoteumamanha.dto.response.NecessidadesResponse;
import br.com.ages.adoteumamanha.exception.ApiException;
import br.com.ages.adoteumamanha.exception.Mensagem;
import br.com.ages.adoteumamanha.mapper.NecessidadeResponseMapper;
import br.com.ages.adoteumamanha.mapper.NecessidadesResponseMapper;
import br.com.ages.adoteumamanha.mapper.PedidoMapper;
import br.com.ages.adoteumamanha.repository.MatchRepository;
import br.com.ages.adoteumamanha.repository.PedidoRepository;
import br.com.ages.adoteumamanha.repository.UsuarioRepository;
import br.com.ages.adoteumamanha.security.UserPrincipal;
import br.com.ages.adoteumamanha.validator.CadastrarPedidoRequestValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static br.com.ages.adoteumamanha.domain.enumeration.Status.PENDENTE;
import static br.com.ages.adoteumamanha.dto.response.NecessidadesResponse.NecessidadeResponse;
import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.BooleanUtils.isFalse;
import static org.springframework.data.domain.Sort.Direction;
import static org.springframework.data.domain.Sort.by;

@Slf4j
@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository repository;

    public void cadastrar(final Long idDoador, final Long idNecessidade, final LocalDateTime data, final String descricao) {
        final Match match = new Match(idDoador, idNecessidade, data, descricao);

        log.info("Cadastrando novo match, do usuario com id: {}, para a necessidade da casa com id {}", idDoador, idNecessidade);
        repository.save(match);
    }

}
