package br.com.ages.adoteumamanha.service.match;

import br.com.ages.adoteumamanha.domain.entity.Match;
import br.com.ages.adoteumamanha.domain.entity.Pedido;
import br.com.ages.adoteumamanha.domain.entity.Usuario;
import br.com.ages.adoteumamanha.dto.response.DescricaoMatchResponse;
import br.com.ages.adoteumamanha.dto.response.ResumoMatchResponse;
import br.com.ages.adoteumamanha.exception.ApiException;
import br.com.ages.adoteumamanha.exception.Mensagem;
import br.com.ages.adoteumamanha.mapper.DescricaoMatchResponseMapper;
import br.com.ages.adoteumamanha.mapper.MatchMapper;
import br.com.ages.adoteumamanha.mapper.ResumoMatchResponseMapper;
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

        log.info("Enviando email aos Admins");
        sendEmail(match);

        log.info("Cadastrando match da doação id: {} com necessidade id {} pelo administrador", idDoacao, idNecessidade);
        return descricaoMatchResponseMapper.apply(repository.save(match));

    }

    public void sendEmail(Match match) {
        List<String> users = usuarioRepository.findByPerfilAndAtivo(ADMIN, true).stream()
                .map(Usuario::getEmail)
                .collect(Collectors.toList());


        String assunto = match.getNecessidade().getAssunto();
        String data = match.getDataCriacao().toString();
        String categoria = match.getNecessidade().getCategoria().name();
        String subCategoria = match.getNecessidade().getSubcategoria().name();
        String descricao = match.getDescricao();
        String nomeCasa = match.getNecessidade().getUsuario().getNome();
        String nomeDoador = match.getDoacao().getUsuario().getNome();

        String[] emails = users.toArray(new String[0]);
        String subject = "Novo vínculo cadastrado!";
        String text = "<h1>Novo Match Cadastrado!<h1>\n";
                text += "<h2>Assunto: " + assunto + "</h2>\n";
                text += "<h3>Data: " + data + "</h3>\n";
                text += "<h3>Categoria: " + categoria + "</h3>\n";
                text += "<h3>SubCategoria: " + subCategoria + "</h3>\n";
                text += "<p>Descrição: " + descricao + "</p>\n";
                text += "<h3>Casa: " + nomeCasa + "</h3>\n";
                text += "<h3>Doador: " + nomeDoador + "</h3>\n";
        mailService.sendEmail(emails, subject, text, true);
    }
}

