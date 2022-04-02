package br.com.ages.adoteumamanha.security;

import br.com.ages.adoteumamanha.dto.request.LoginRequest;
import br.com.ages.adoteumamanha.dto.response.LoginResponse;
import br.com.ages.adoteumamanha.validator.LoginRequestValidator;
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
    private final LoginRequestValidator validator;

    public LoginResponse authenticate(final LoginRequest request) {

        log.info("Validando request de login");
        validator.validate(request);

        log.info("Autenticando o usuário: {}", request.getEmail());
        final Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getSenha()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("Usuário {} autenticado", request.getEmail());

        return LoginResponse.builder()
                .accessToken(HEADER_PREFIX + jwtTokenProvider.generateToken(authentication))
                .build();
    }
}
