package br.com.ages.adoteumamanha.service.match;

import br.com.ages.adoteumamanha.domain.entity.Match;
import br.com.ages.adoteumamanha.domain.entity.Pedido;
import br.com.ages.adoteumamanha.domain.entity.Usuario;
import br.com.ages.adoteumamanha.dto.response.DescricaoMatchResponse;
import br.com.ages.adoteumamanha.exception.ApiException;
import br.com.ages.adoteumamanha.exception.Mensagem;
import br.com.ages.adoteumamanha.mapper.DescricaoMatchResponseMapper;
import br.com.ages.adoteumamanha.mapper.MatchMapper;
import br.com.ages.adoteumamanha.repository.MatchRepository;
import br.com.ages.adoteumamanha.repository.UsuarioRepository;
import br.com.ages.adoteumamanha.service.mail.MailService;
import br.com.ages.adoteumamanha.security.UserPrincipal;
import br.com.ages.adoteumamanha.service.pedidos.BuscarPedidoService;
import br.com.ages.adoteumamanha.validator.MatchValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static br.com.ages.adoteumamanha.domain.enumeration.Perfil.ADMIN;
import static br.com.ages.adoteumamanha.domain.enumeration.Status.FINALIZADA;
import static java.util.Objects.isNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class MatchAdminService {
    private final UsuarioRepository usuarioRepository;
    private final MailService mailService;
    private final MatchRepository repository;
    private final MatchMapper matchMapper;
    private final MatchValidator matchValidator;
    private final BuscarPedidoService buscarPedidoService;
    private final DescricaoMatchResponseMapper descricaoMatchResponseMapper;

    public DescricaoMatchResponse cadastrar(final Long idDoacao, final Long idNecessidade, final UserPrincipal userPrincipal) {

        log.info("Validando id da doação {} e id da necessidade {}", idDoacao, idNecessidade);
        if (isNull(idDoacao) || isNull(idNecessidade)) {
            throw new ApiException(Mensagem.REQUEST_INVALIDO.getDescricao(), HttpStatus.BAD_REQUEST);
        }
        log.info("Buscando no banco o id da doação {}", idDoacao);
        final Pedido doacao = buscarPedidoService.buscarPorID(idDoacao);

        log.info("Buscando no banco o id da necessidade {}", idNecessidade);
        final Pedido necesidade = buscarPedidoService.buscarPorID(idNecessidade);

        final Match match = matchMapper.apply(userPrincipal, doacao, necesidade);

        log.info("validando se o match foi mapeado corretamente");
        matchValidator.validar(match);

        log.info("Atualizando os status da necessidade e doação para {}", FINALIZADA);
        necesidade.setStatus(FINALIZADA);
        doacao.setStatus(FINALIZADA);

        log.info("Cadastrando match da doação id: {} com necessidade id {} pelo administrador", idDoacao, idNecessidade);
        Match savedMatch = repository.save(match);

        log.info("Enviando email aos Admins");
        sendEmail(match);

        return descricaoMatchResponseMapper.apply(savedMatch);

    }

    private void sendEmail(Match match) {
        new Thread(() -> {
            String[] emails = usuarioRepository.findByPerfilAndAtivo(ADMIN, true).stream()
                    .map(Usuario::getEmail)
                    .toArray(String[]::new);

            String assunto = match.getNecessidade().getAssunto();
            String data = match.getDataCriacao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            String categoria = match.getNecessidade().getCategoria().name();
            String subCategoria = match.getNecessidade().getSubcategoria().name();
            String descricao = match.getDescricao();
            String descricaoNecessidade = match.getNecessidade().getDescricao();
            String descricaoDoacao = match.getDoacao().getDescricao();
            String nomeCasa = match.getNecessidade().getUsuario().getNome();
            String nomeDoador = match.getDoacao().getUsuario().getNome();

            String subject = "Novo vínculo cadastrado!";
            String text = "<h1>Um novo vínculo foi cadastrado!</h1>\n";
            text += "<p>Descrição: " + descricao + "</p>\n";
            text += "<p>Data de cadastro: " + data + "</p>\n";
            text += "<p>Assunto: " + assunto + "</p>\n";
            text += "<p>Categoria: " + categoria + "</p>\n";
            text += "<p>Sub categoria: " + subCategoria + "</p>\n";
            text += "<p>Casa: " + nomeCasa + "</p>\n";
            text += "<p>Descrição da necessidade: " + descricaoNecessidade + "</p>\n";
            text += "<p>Doador: " + nomeDoador + "</p>\n";
            text += "<p>Descrição da doação: " + descricaoDoacao + "</p>\n";
            mailService.sendEmail(emails, subject, text, true);
        }).start();
    }
}