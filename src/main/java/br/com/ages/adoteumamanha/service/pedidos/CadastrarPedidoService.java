package br.com.ages.adoteumamanha.service.pedidos;

import br.com.ages.adoteumamanha.domain.entity.Pedido;
import br.com.ages.adoteumamanha.domain.entity.Usuario;
import br.com.ages.adoteumamanha.domain.enumeration.Perfil;
import br.com.ages.adoteumamanha.dto.request.CadastrarPedidoRequest;
import br.com.ages.adoteumamanha.mapper.PedidoMapper;
import br.com.ages.adoteumamanha.repository.PedidoRepository;
import br.com.ages.adoteumamanha.security.UserPrincipal;
import br.com.ages.adoteumamanha.validator.CadastrarPedidoRequestValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import br.com.ages.adoteumamanha.service.mail.MailService;
import java.util.List;
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
        sendEmail(entity);
        log.info("Enviando email aos Admins");
    }
    private void sendEmail(Pedido pedido) {
        new Thread(() -> {
            List<String> users = usuarioRepository.findByPerfilAndAtivo(ADMIN, true).stream()
                    .map(Usuario::getEmail)
                    .collect(Collectors.toList());
            String[] emails = users.toArray(new String[0]);

            String assunto = pedido.getAssunto();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String data = pedido.getDataHora().format(formatter);
            String categoria = pedido.getCategoria().name();
            String subCategoria = pedido.getSubcategoria().name();
            String descricao = pedido.getDescricao();
            String tipo;
            Perfil perfil;
            if(pedido.getTipoPedido() == DOACAO){
                tipo = "doação";
                perfil = DOADOR;
            } else {
                tipo = "necessidade";
                perfil = CASA;
            }

            String nomeUsuario = usuarioRepository.findByPerfilAndId(perfil, pedido.getUsuario().getId()).getNome();
            String subject = "Nova " + tipo + " cadastrada!";
            String text = "<h1>Uma nova " + tipo + " foi cadastrada!<h1>\n";
            text += "<h2>Algumas informações importantes da " + tipo + " cadastrada</h2>\n";
            text += "<h2>Assunto: " + assunto + "</h2>\n";
            text += "<h3>Data: " + data + "</h3>\n";
            text += "<h3>Categoria: " + categoria + "</h3>\n";
            text += "<h3>Subcategoria: " + subCategoria + "</h3>\n";
            text += "<h4>Usuário: " + nomeUsuario + "</h4>\n";
            text += "<p>Descrição: " + descricao + "</p>\n";
            mailService.sendEmail(emails, subject, text, true);
        }).start();
    }
}