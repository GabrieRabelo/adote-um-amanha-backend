package br.com.ages.adoteumamanha.security;

import br.com.ages.adoteumamanha.dto.response.LoginResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private static final String HEADER_PREFIX = "Bearer ";

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public LoginResponse authenticate(final String username, final String password) {

        log.info("Autenticando o usuário: {}", username);
        final Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("Usuário {} autenticado", username);

        return LoginResponse.builder()
                .accessToken(HEADER_PREFIX + jwtTokenProvider.generateToken(authentication))
                .build();
    }
}
