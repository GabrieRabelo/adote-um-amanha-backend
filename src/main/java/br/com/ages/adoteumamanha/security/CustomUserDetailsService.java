package br.com.ages.adoteumamanha.security;

import br.com.ages.adoteumamanha.domain.entity.UsuarioEntity;
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
        UsuarioEntity usuarioEntity = getUser(() -> usuarioRepository.findById(id));
        return UserPrincipal.create(usuarioEntity);
    }

    @Override
    public UserDetails loadUserByUsername(final String username) {
        log.info("Buscando usuário pelo username: {}", username);
        UsuarioEntity usuarioEntity = getUser(() -> usuarioRepository.findByEmail(username));
        return UserPrincipal.create(usuarioEntity);
    }

    private UsuarioEntity getUser(final Supplier<Optional<UsuarioEntity>> supplier) {

        //TODO erro mensagem criar padrão como na api trab
        return supplier.get().orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }

}

