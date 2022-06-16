package br.com.ages.adoteumamanha.service.pedidos;

import br.com.ages.adoteumamanha.domain.entity.Pedido;
import br.com.ages.adoteumamanha.domain.entity.Usuario;
import br.com.ages.adoteumamanha.domain.enumeration.Perfil;
import br.com.ages.adoteumamanha.dto.request.CadastrarPedidoRequest;
import br.com.ages.adoteumamanha.exception.ApiException;
import br.com.ages.adoteumamanha.exception.Mensagem;
import br.com.ages.adoteumamanha.mapper.PedidoMapper;
import br.com.ages.adoteumamanha.repository.PedidoRepository;
import br.com.ages.adoteumamanha.security.UserPrincipal;
import br.com.ages.adoteumamanha.validator.CadastrarPedidoRequestValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import br.com.ages.adoteumamanha.service.mail.MailService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import br.com.ages.adoteumamanha.repository.UsuarioRepository;
import java.time.format.DateTimeFormatter;

import java.util.List;
import java.util.stream.Collectors;

import static br.com.ages.adoteumamanha.domain.enumeration.Perfil.*;
import static br.com.ages.adoteumamanha.domain.enumeration.TipoPedido.DOACAO;

@Slf4j
@Service
@RequiredArgsConstructor
public class CadastrarPedidoService {
    private final UsuarioRepository usuarioRepository;
    private final MailService mailService;
    private final PedidoMapper pedidoMapper;
    private final PedidoRepository repository;
    private final CadastrarPedidoRequestValidator validator;

    public void cadastrar(final CadastrarPedidoRequest request, final UserPrincipal userPrincipal) {

        log.info("Validando request de cadastro do pedido");
        validator.validar(request);

        final Pedido entity = pedidoMapper.apply(request, userPrincipal);

        log.info("Cadastrando pedido para o usuário com id: {}", userPrincipal.getId());
        repository.save(entity);
        if(entity.getTipoPedido() == DOACAO){
            log.info("Enviando email aos Admins");
            sendEmail(entity);
        }
    }
    private void sendEmail(Pedido pedido) {
        new Thread(() -> {
            String[] emails = usuarioRepository.findByPerfilAndAtivo(ADMIN, true).stream()
                    .map(Usuario::getEmail)
                    .toArray(String[]::new);

            String assunto = pedido.getAssunto();
            String data = pedido.getDataHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            String categoria = pedido.getCategoria().name();
            String subCategoria = pedido.getSubcategoria().name();
            String descricao = pedido.getDescricao();
            Optional<Usuario> user = usuarioRepository.findById(pedido.getUsuario().getId());
            String nomeUsuario;
            if(user.isPresent()){
                nomeUsuario = user.get().getNome();
            } else {
                throw new ApiException(Mensagem.USUARIO_NAO_ENCONTRADO.getDescricao(), HttpStatus.NOT_FOUND);
            }
            String subject = "Nova doação cadastrada!";
            String text = "<h1>Uma nova doação foi cadastrada!</h1>\n";
            text += "<p>Algumas informações importantes da doação cadastrada</p>\n";
            text += "<p>Assunto: " + assunto + "</p>\n";
            text += "<p>Data: " + data + "</p>\n";
            text += "<p>Categoria: " + categoria + "</p>\n";
            text += "<p>Subcategoria: " + subCategoria + "</p>\n";
            text += "<p>Usuário: " + nomeUsuario + "</p>\n";
            text += "<p>Descrição: " + descricao + "</p>\n";
            mailService.sendEmail(emails, subject, text, true);
        }).start();
    }
}