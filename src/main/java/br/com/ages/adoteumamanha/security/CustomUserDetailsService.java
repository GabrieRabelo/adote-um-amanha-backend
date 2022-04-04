package br.com.ages.adoteumamanha.security;

import br.com.ages.adoteumamanha.domain.entity.Usuario;
import br.com.ages.adoteumamanha.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Supplier;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UserDetails loadUserById(final Long id) {
        log.info("Buscando usuário pelo id: {}", id);
        Usuario usuario = getUser(() -> usuarioRepository.findById(id));
        return UserPrincipal.create(usuario);
    }

    @Override
    public UserDetails loadUserByUsername(final String username) {
        log.info("Buscando usuário pelo username: {}", username);
        Usuario usuario = getUser(() -> usuarioRepository.findByEmail(username));
        return UserPrincipal.create(usuario);
    }

    private Usuario getUser(final Supplier<Optional<Usuario>> supplier) {

        //TODO erro mensagem criar padrão como na api trab
        return supplier.get().orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }

}

