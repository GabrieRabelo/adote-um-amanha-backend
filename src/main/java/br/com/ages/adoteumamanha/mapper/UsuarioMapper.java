package br.com.ages.adoteumamanha.mapper;

import br.com.ages.adoteumamanha.domain.entity.Endereco;
import br.com.ages.adoteumamanha.domain.entity.Usuario;
import br.com.ages.adoteumamanha.domain.enumeration.Perfil;
import br.com.ages.adoteumamanha.dto.request.CadastrarUsuarioRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

import static java.lang.Boolean.TRUE;

@Component
public class UsuarioMapper implements BiFunction<CadastrarUsuarioRequest, Perfil, Usuario> {

    @Override
    public Usuario apply(final CadastrarUsuarioRequest request, final Perfil perfil) {

        final PasswordEncoder encoder = new BCryptPasswordEncoder();

        return Usuario.builder()
                .withNome(request.getNome())
                .withEmail(request.getEmail())
                .withSenha(encoder.encode(request.getSenha()))
                .withDocumento(request.getDocumento())
                .withTelefone(request.getTelefone())
                .withSite(request.getSite())
                .withEndereco(buildEndereco(request))
                .withAtivo(TRUE)
                .withPerfil(perfil)
                .build();

    }

    private Endereco buildEndereco(final CadastrarUsuarioRequest usuarioRequest) {
        return Endereco.builder()
                .withEstado(usuarioRequest.getEstado())
                .withCidade(usuarioRequest.getCidade())
                .withBairro(usuarioRequest.getBairro())
                .withCEP(usuarioRequest.getCep())
                .withRua(usuarioRequest.getRua())
                .withNumero(usuarioRequest.getNumero())
                .withComplemento(usuarioRequest.getComplemento())
                .build();
    }

}