package br.com.ages.adoteumamanha.mapper;

import br.com.ages.adoteumamanha.domain.entity.Endereco;
import br.com.ages.adoteumamanha.domain.entity.Usuario;
import br.com.ages.adoteumamanha.domain.enumeration.Perfil;
import br.com.ages.adoteumamanha.dto.request.CadastrarUsuarioRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.function.Function;
import static br.com.ages.adoteumamanha.domain.enumeration.Status.PENDENTE;

@Component
public class UsuarioMapper implements Function<CadastrarUsuarioRequest,Usuario> {


    @Override
    public Usuario apply(@NotNull final CadastrarUsuarioRequest cadastrarUsuarioRequest) {

        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return Usuario.builder()
                .withNome(cadastrarUsuarioRequest.getNome())
                .withDocumento(cadastrarUsuarioRequest.getDocumento())
                .withEndereco(buildEndereco(cadastrarUsuarioRequest))
                .withEmail(cadastrarUsuarioRequest.getEmail())
                .withTelefone(cadastrarUsuarioRequest.getTelefone())
                .withSenha(encoder.encode(cadastrarUsuarioRequest.getSenha()))
                .withAtivo(true)
                .withPerfil(Perfil.DOADOR)
                .withSite("")
                //.withStatus(PENDENTE)
                .build();

    }

    private Endereco buildEndereco(final CadastrarUsuarioRequest usuarioRequest) {
        return Endereco.builder()
                .withBairro(usuarioRequest.getBairro())
                .withCEP(usuarioRequest.getCep())
                .withComplemento(usuarioRequest.getComplemento())
                .withEstado(usuarioRequest.getEstado())
                .withCidade(usuarioRequest.getCidade())
                .withNumero(usuarioRequest.getNumero())
                .withRua(usuarioRequest.getRua())
                .build();

    }


}
